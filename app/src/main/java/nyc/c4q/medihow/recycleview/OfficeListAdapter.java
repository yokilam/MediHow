package nyc.c4q.medihow.recycleview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nyc.c4q.medihow.R;
import nyc.c4q.medihow.activites.MapsActivity;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);

        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, int position) {
        final MedicareOffice office = officeList.get(position);
        holder.businessNumber.setText(office.getPhone_number());
        holder.businessNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:" +office.getPhone_number()));
                holder.context.startActivity(call);
            }
        });

        holder.businessName.setText(office.getName_of_medical_office());
        holder.businessName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapsActivity.moveCamera(office.getName_of_medical_office());
            }
        });
    }

    @Override
    public int getItemCount() {
        return officeList.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        TextView businessName, businessNumber;
        Context context;

        public ListHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            businessName = itemView.findViewById(R.id.office_title);
            businessNumber = itemView.findViewById(R.id.office_number);
        }
    }
}
