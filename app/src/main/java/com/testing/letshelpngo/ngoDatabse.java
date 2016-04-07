package com.testing.letshelpngo;



public class ngoDatabse {


    private int _id;
    private String _name;
    private String _activity;
    private String _description;
    private String _contact;

    public ngoDatabse(Integer id, String name, String description, String activty, String contact) {
        this._id = id;
        this._name = name;
        this._activity = activty;
        this._description = description;
        this._contact = contact;
    }

    public ngoDatabse() {
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_activity(String _activty) {
        this._activity = _activty;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public void set_contact(String _contact) {
        this._contact = _contact;

    }


    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public String get_activity() {
        return _activity;
    }

    public String get_contact() {
        return _contact;
    }

    public String get_description() {
        return _description;
    }
}

