package com.example.mynotes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mynotes.Models.Notes;
import com.example.mynotes.NotesClickListener;
import com.example.mynotes.R;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesListAdapter extends RecyclerView.Adapter <NotesViewHolder> {

    Context context;
    List<Notes> list;

    NotesClickListener listener;

    public NotesListAdapter(Context context, List<Notes> list, NotesClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NotNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotesViewHolder holder, int position) {


        // для того, чтобы звкрпять отдельные заметки
        holder.textView_title.setText(list.get(position).getTitle());
        holder.textView_title.setSelected(true);

        holder.textView_notes.setText(list.get(position).getNotes());


        holder.textView_date.setText(list.get(position).getDate());
        holder.textView_date.setSelected(true);

        if (list.get(position).getPinned()) {
         holder.imageView_pin.setImageResource(R.drawable.pin_icon); // если заметка закрепляется, будет высвечиваться знак pin_icon
        } else {
            holder.imageView_pin.setImageResource(0);  // если заметка не закрепляется, значок не появляется.
        }
    int color_code = getRandomColor();
        holder.notes_conteiner.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code, null));


        // метод для активации кликов. обычный и длинный клик для активации разных действий
        holder.notes_conteiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });
        holder.notes_conteiner.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(list.get(holder.getAdapterPosition()), holder.notes_conteiner);
                return true;
            }
        });


    }

    // метод случайного набора цветов для бэккграунда заметок
    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.gray);
        colorCode.add(R.color.theLightest_gray);
        colorCode.add(R.color.light_gray);
        colorCode.add(R.color.pink_gray);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Notes> filteredList) {
        list = filteredList;
        notifyDataSetChanged();

    }

}

class NotesViewHolder extends RecyclerView.ViewHolder {

    CardView notes_conteiner;
    TextView textView_title, textView_notes, textView_date;
    ImageView imageView_pin;

    public NotesViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        notes_conteiner = itemView.findViewById(R.id.notes_conteiner);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_notes = itemView.findViewById(R.id.textView_notes);
        textView_date = itemView.findViewById(R.id.textView_date);
        imageView_pin = itemView.findViewById(R.id.imageView_pin);
    }
}
