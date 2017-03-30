package team.unicorn.vsb.twoclicks.activity.customadapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import team.unicorn.vsb.twoclicks.R;
import team.unicorn.vsb.twoclicks.activity.constant.Constant;
import team.unicorn.vsb.twoclicks.activity.model.Job;

/**
 * Created by lenovo on 27/1/17.
 * 1. where WE INFLATE OUR MODEL LAYOUT INTO VIEW ITEM
 * 2. THEN BIND DATA
 */

public class ProductCustomAdapter extends RecyclerView.Adapter<ProductCustomAdapter.ViewHolderProducts> {
    
  
    private Context context;
    private List<Job> products;
    
    public ProductCustomAdapter(Context context, List<Job> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public ViewHolderProducts onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderProducts(context, LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_jobs, parent, false));
    }


    @Override
    public void onBindViewHolder(ViewHolderProducts holder, int position) {

        Job product = products.get(position);

        // Set item
        holder.getItem().setText(product.getJobcard().toString());

        // Set location
        holder.getLocation().setText(product.getName() + " - " + product.getPanchayath());

        if (product.getDemandStatus().equals("True"))
        {
            holder.imageView.setImageResource(R.drawable.approval);
        }
        else
        {
            holder.imageView.setImageResource(R.drawable.notapproval);
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void refill(Job product) {
        products.add(product);
        notifyDataSetChanged();
    }

    public void changeUser(int index, Job product) {
        products.set(index,product);
        notifyDataSetChanged();
    }

    public void clear() {
        products.clear();
    }


    /* ViewHolder for RecyclerView */
     class ViewHolderProducts extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textView_item;
        private TextView textView_location;
        private ImageView imageView;

        private Context mContextViewHolder;

         ViewHolderProducts(Context context, View itemView) {
            super(itemView);
            textView_item = (TextView)itemView.findViewById(R.id.nameTxt);
            textView_location = (TextView)itemView.findViewById(R.id.locationTxt);
             imageView = (ImageView)itemView.findViewById(R.id.img_icon);

             mContextViewHolder = context;

            itemView.setOnClickListener(this);
        }

         TextView getItem() {
            return textView_item;
        }
         TextView getLocation() {
            return textView_location;
        }
         ImageView getImageView() {return imageView;}

        @Override
        public void onClick(View view) {

            final Job product = products.get(getLayoutPosition());

            final Dialog dialog = new Dialog(context);

            //setting custom layout to dialog
            dialog.setContentView(R.layout.layout_job_details_view);
            dialog.setTitle("JobCard Details");
            dialog.setCanceledOnTouchOutside(false);

            final EditText edittext_item,edittext_quantity,edittext_price,editText_seller,editText_unit,editText_date;

            edittext_item = (EditText)dialog.findViewById(R.id.item);
            edittext_quantity = (EditText)dialog.findViewById(R.id.qty);
            edittext_price = (EditText)dialog.findViewById(R.id.price);
            editText_seller = (EditText)dialog.findViewById(R.id.seller);
            editText_unit = (EditText)dialog.findViewById(R.id.unit);
            editText_date = (EditText)dialog.findViewById(R.id.date);

            edittext_item.setText(product.getName());
            edittext_quantity.setText(product.getMobileno());
            edittext_price.setText(product.getPanchayath());
            editText_seller.setText(product.getJobcard());
            editText_unit.setText(product.getDemandStatus());
            editText_date.setText(product.getDuration());

            //adding button click event
            Button dismissButton = (Button) dialog.findViewById(R.id.btn_ok);
            dismissButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            //adding button click event
            Button shareButton = (Button) dialog.findViewById(R.id.btn_share);
            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, " Name : " + product.getName() + "\n JobCard No. : " + product.getJobcard() +"\n Mobile No. : "+ product.getMobileno()
                    + "\n Panchayath : " + product.getPanchayath() + "\n Demand Status : " + product.getDemandStatus() + "\n Duration : " + product.getDuration());
                    sendIntent.setType("text/plain");
                    sendIntent.setPackage("com.whatsapp");
                    context.startActivity(sendIntent);
                }
            });
            dialog.show();

        }
    }


}
