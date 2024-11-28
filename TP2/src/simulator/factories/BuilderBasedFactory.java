package simulator.factories;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import java.util.*;
import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	private Map<String, Builder<T>> _builders;
	private List<JSONObject> _builders_info;

	public BuilderBasedFactory() {
		this._builders = new HashMap<>();
		this._builders_info = new ArrayList<>();
	}

	public BuilderBasedFactory(List<Builder<T>> builders) {
		this();
		for (Builder<T> builder : builders) {
            add_builder(builder);
        }
	}

	public void add_builder(Builder<T> b) {
	    String tag = b.get_type_tag();
	    this._builders.put(tag, b);
	    this._builders_info.add(b.get_info());
	}

	@Override
	public T create_instance(JSONObject info) {
		if (info == null) {
            throw new IllegalArgumentException("’info’ cannot be null");
        }
        String type = info.getString("type");
        Builder<T> builder = this._builders.get(type);
        if (builder == null) {
            throw new IllegalArgumentException("Unrecognized ‘info’: " + info.toString());
        }
        JSONObject data = info.has("data") ? info.getJSONObject("data") : new JSONObject();
        T instance = builder.create_instance(data);
        if (instance == null) {
            throw new IllegalArgumentException("Failed to create instance: " + info.toString());
        }
        return instance;
	}

	@Override
	public List<JSONObject> get_info() {
		return Collections.unmodifiableList(_builders_info);
	}

}