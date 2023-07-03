package etu1784.framework;

import com.google.gson.Gson;

import java.util.HashMap;

public class ModelView {
    private String view;
    private HashMap<String, Object> data;
    private HashMap<String, String> session;
    private boolean json;
    private final Gson gson;

    public ModelView() {
        data = new HashMap<>();
        session = new HashMap<>();
        json = false;
        gson = new Gson();
    }

    public String toJson() {
        return gson.toJson(data);
    }

    public void addItem(String key, Object value) {
        data.put(key, value);
    }

    public void addSession(String key, String value) {
        session.put(key, value);
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public HashMap<String, String> getSession() {
        return session;
    }

    public void setSession(HashMap<String, String> session) {
        this.session = session;
    }

    public boolean isJson() {
        return json;
    }

    public void setJson(boolean json) {
        this.json = json;
    }
}
