package com.example.travhelp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

//Used to list all the patients
//Uses the template in the  layout_list_patients.xml file
public class CustomAdapterSelection extends BaseAdapter {
    private Context mContext;
    SelectionDbHelper controldb;
    private ArrayList<String> Name = new ArrayList<String>();
    private ArrayList<String> Surname = new ArrayList<String>();
    private ArrayList<String> Time_min = new ArrayList<String>();
    private ArrayList<String> Time_max = new ArrayList<String>();
    private ArrayList<String> Duration = new ArrayList<String>();
    private ArrayList<String> Address = new ArrayList<String>();


    public CustomAdapterSelection(Context  context, ArrayList<String> Name, ArrayList<String> Surname, ArrayList<String> Address, ArrayList<String> Time_min, ArrayList<String> Time_max, ArrayList<String> Duration)
    {
        this.mContext = context;
        this.Name = Name;
        this.Surname = Surname;
        this.Address = Address;
        this.Time_min = Time_min;
        this.Time_max = Time_max;
        this.Duration = Duration;
    }

    @Override
    public int getCount() {
        return Name.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    //The getView method will be called getCount() times, and will add up the different patients
    //thus building the final list of all patients
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final    viewHolder holder;
        controldb = new SelectionDbHelper(mContext);
        LayoutInflater layoutInflater;

        //if the patient's layout has not yet been done, we fill it in
        if (convertView == null) {
            //To inflate the patient's template
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_list_selection, null);

            //Creates a viewHolder with 2 TextViews : name and surname
            holder = new viewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.tvname);
            holder.surname = (TextView) convertView.findViewById(R.id.tvsurname);
            holder.address = (TextView) convertView.findViewById(R.id.tvaddress);
            holder.time_max = (EditText)convertView.findViewById(R.id.ettime_max);
            holder.time_min = (EditText)convertView.findViewById(R.id.ettime_min);
            holder.duration = (EditText)convertView.findViewById(R.id.etduration);

            //Fill in the viewHolder
            holder.name.setText(Name.get(position));
            holder.surname.setText(Surname.get(position));
            holder.address.setText(Address.get(position));
            /*holder.time_max.setText(Time_max.get(position));
            holder.time_min.setText(Time_min.get(position));
            holder.duration.setText(Duration.get(position));*/

        }
        //Set the ID in the patient's tag, to know which one to display in the database

        System.out.println("Tag dans le convert view = " + convertView.getTag());
        return convertView;
    }
    public class viewHolder {
        TextView name;
        TextView surname;
        TextView address;
        EditText time_min;
        EditText time_max;
        EditText duration;

    }

}
