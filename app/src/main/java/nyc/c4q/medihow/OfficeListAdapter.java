package nyc.c4q.medihow;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nyc.c4q.medihow.model.MedicareOffice;

/**
 * Created by jervon.arnoldd on 3/4/18.
 */

public class OfficeListAdapter extends RecyclerView.Adapter<OfficeListAdapter.ListHolder> {

    List<MedicareOffice> officeList;



    public OfficeListAdapter(List<MedicareOffice> officeList) {
        this.officeList = officeList;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);

        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {
//        MedicareOffice office = officeList.get(position);
       MedicareOffice office = officeList.get(position);

        holder.businessNumber.setText(office.getPhone_number());
        holder.businessName.setText(office.getName_of_medical_office());


    }

    @Override
    public int getItemCount() {
        return officeList.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        TextView businessName,businessNumber;
        public ListHolder(View itemView) {
            super(itemView);
            businessName =itemView.findViewById(R.id.office_title);
            businessNumber=itemView.findViewById(R.id.office_number);
        }
    }
}
