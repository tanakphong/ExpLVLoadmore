package com.deverdie.explvloadmore;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by tanakorn.pho on 19/05/2560.
 */

public class ExpendableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ExpHeaderBean> listDataHeader;
    private HashMap<ExpHeaderBean,List<ExpListBean>> listHashMap;



    public ExpendableListAdapter(Context context, List<ExpHeaderBean> listDataHeader, HashMap<ExpHeaderBean, List<ExpListBean>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    public void setNewItems(List<ExpHeaderBean> listDataHeader,HashMap<ExpHeaderBean, List<ExpListBean>> listChildData) {
        this.listDataHeader = listDataHeader;
        this.listHashMap = listChildData;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        int c=0;
        try{
            c=listDataHeader.size();
        }catch (Exception aE) {

        }
        return c;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int c=0;
        try{
            c=listHashMap.get(listDataHeader.get(groupPosition)).size();
        }catch (Exception aE) {

        }
        return c;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHeaderHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater=(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.lst_group,null);
            holder = new ViewHeaderHolder();
            holder.txtBatch = (TextView) convertView.findViewById(R.id.lblBatch);
            holder.txtMaterialText = (TextView) convertView.findViewById(R.id.lblMaterialText);
            holder.txtWeightQTY = (TextView) convertView.findViewById(R.id.lblWeightQty);
            holder.txtWeightUnit = (TextView) convertView.findViewById(R.id.lblWeightUnit);
            convertView.setTag(holder);
        } else {
            //
            holder = (ViewHeaderHolder) convertView.getTag();
        }
        ExpHeaderBean data = (ExpHeaderBean) getGroup(groupPosition);
        holder.txtBatch.setText(data.get_batch());
        holder.txtBatch.setTypeface(null, Typeface.BOLD);
        holder.txtMaterialText.setText(data.get_material_text());
        holder.txtWeightQTY.setText(data.get_qty().toString());
        holder.txtWeightUnit.setText(data.get_unit());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewListHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater=(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.lst_item,null);
            holder = new ViewListHolder();
            holder.txtPlant = (TextView) convertView.findViewById(R.id.lblPlant);
            holder.txtMaterial = (TextView) convertView.findViewById(R.id.lblMaterial);
            convertView.setTag(holder);
        } else {
            //
            holder = (ViewListHolder) convertView.getTag();
        }
        ExpListBean data = (ExpListBean) getChild(groupPosition,childPosition);
        holder.txtPlant.setText(data.get_plant());
        holder.txtMaterial.setText(data._material);
        /*   String childText = (String)getChild(groupPosition,childPosition);
        if (convertView==null){
            LayoutInflater inflater=(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.lst_item,null);
        }
        TextView lblListItem = (TextView) convertView.findViewById(R.id.lblListItem);
        lblListItem.setText(childText);*/
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public class ViewHeaderHolder {
        TextView txtBatch;
        TextView txtMaterialText;
        TextView txtWeightQTY;
        TextView txtWeightUnit;
    }
    public class ViewListHolder {
        TextView txtPlant;
        TextView txtMaterial;
    }
}
