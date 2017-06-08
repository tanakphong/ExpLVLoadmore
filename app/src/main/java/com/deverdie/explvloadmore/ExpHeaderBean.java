package com.deverdie.explvloadmore;

/**
 * Created by tanakorn.pho on 23/05/2560.
 */

public class ExpHeaderBean {
    String _batch;
    String _material_text;
    Float _qty;
    String _unit;

    public ExpHeaderBean(String _batch, String _material_text, Float _qty, String _unit) {
        this._batch = _batch;
        this._material_text = _material_text;
        this._qty = _qty;
        this._unit = _unit;
    }

    public String get_batch() {
        return _batch;
    }

    public void set_batch(String _batch) {
        this._batch = _batch;
    }

    public String get_material_text() {
        return _material_text;
    }

    public void set_material_text(String _material_text) {
        this._material_text = _material_text;
    }

    public Float get_qty() {
        return _qty;
    }

    public void set_qty(Float _qty) {
        this._qty = _qty;
    }

    public String get_unit() {
        return _unit;
    }

    public void set_unit(String _unit) {
        this._unit = _unit;
    }
}
