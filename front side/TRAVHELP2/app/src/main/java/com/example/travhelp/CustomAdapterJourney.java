package com.example.travhelp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;


//Used to list all the patients
//Uses the template in the  layout_list_patients.xml file
public class CustomAdapterJourney extends BaseAdapter {
    private Context mContext;
    PatientsDbHelper controldb;
    private ArrayList<String> Name = new ArrayList<String>();
    private ArrayList<String> Surname = new ArrayList<String>();
    private ArrayList<String> Time = new ArrayList<String>();
    private ArrayList<String> Address = new ArrayList<String>();


    public CustomAdapterJourney(Context  context, ArrayList<String> Name, ArrayList<String> Surname, ArrayList<String> Address, ArrayList<String> Time)
    {
        this.mContext = context;
        this.Name = Name;
        this.Surname = Surname;
        this.Address = Address;
        this.Time = Time;
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
        controldb = new PatientsDbHelper(mContext);
        LayoutInflater layoutInflater;

        //if the patient's layout has not yet been done, we fill it in
        if (convertView == null) {
            //To inflate the patient's template
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_list_patients_journey, null);

            //Creates a viewHolder with 2 TextViews : name and surname
            holder = new viewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.tvname);
            holder.surname = (TextView) convertView.findViewById(R.id.tvsurname);;
            holder.address = (TextView) convertView.findViewById(R.id.tvaddress);;
            holder.time = (TextView) convertView.findViewById(R.id.tvtime);

            //Fill in the viewHolder
            holder.name.setText(Name.get(position));
            holder.surname.setText(Surname.get(position));
            holder.address.setText(Address.get(position));
            holder.time.setText(Time.get(position));
        }
        //Set the ID in the patient's tag, to know which one to display in the database

        System.out.println("Tag dans le convert view = " + convertView.getTag());
        return convertView;
    }
    public class viewHolder {
        TextView name;
        TextView surname;
        TextView address;
        TextView time;

    }

}
