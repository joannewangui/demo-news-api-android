package wizag.com.kenyabuzz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class CategoriesActivity extends AppCompatActivity {
    ImageButton top_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        top_button = findViewById(R.id.top_button);
        top_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tech = new Intent(CategoriesActivity.this, MainActivity.class);

                startActivity(tech);
            }
        });
    }
}
