package com.deverdie.explvloadmore;

/**
 * Created by tanakorn.pho on 23/05/2560.
 */

public class ExpListBean {
    String _plant;
    String _material;

    public ExpListBean(String _plant, String _material) {
        this._plant = _plant;
        this._material = _material;
    }

    public String get_plant() {
        return _plant;
    }

    public void set_plant(String _plant) {
        this._plant = _plant;
    }

    public String get_material() {
        return _material;
    }

    public void set_material(String _material) {
        this._material = _material;
    }
}
