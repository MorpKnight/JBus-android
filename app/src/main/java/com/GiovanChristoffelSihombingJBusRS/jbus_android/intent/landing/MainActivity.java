package com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.landing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.R;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.adapter.BusArrayAdapter;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.order.MakeBookingActivity;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.order.OrderBusActivity;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.intent.order.history.HistoryListActivity;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Account;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.BaseResponse;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Bus;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.LoggedAccount;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.OrderHistory;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Payment;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.StoredBus;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.BaseAPIService;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public MenuItem searchItem, profileItem, paymentItem;
    public ListView listView;

    public BusArrayAdapter busArrayAdapter;

    private Button[] btns;
    private int currentPage = 0;
    private int pageSize = 12;
    private int listSize, noOfPages;
    private List<Bus> listBus = new ArrayList<>();
    private Button prevButton = null;
    private Button nextButton = null;
    private HorizontalScrollView pageScroll = null;
    private BaseAPIService mApiService;
    public static Bus busSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Bus List");
        listView = findViewById(R.id.busList);
        listView.setAdapter(busArrayAdapter);

        busSelected = null;

        prevButton = findViewById(R.id.prevPage);
        nextButton = findViewById(R.id.nextPage);
        pageScroll = findViewById(R.id.pageNumberScroll);
        mApiService = UtilsApi.getAPIService();
        getBusList();
        getAllPayment();

        if (listBus != null) {
            paginationFooter();
            viewPaginatedList(listBus, currentPage);
        } else {
            viewToast(this, "No Bus Available");
        }

        prevButton.setOnClickListener(v -> {
            if (currentPage > 0) {
                currentPage--;
                goToPage(currentPage);
            }
        });

        nextButton.setOnClickListener(v -> {
            if (currentPage < noOfPages - 1) {
                currentPage++;
                goToPage(currentPage);
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            try {
                busSelected = listBus.get(position);
                startActivity(new Intent(MainActivity.this, OrderBusActivity.class));
            } catch (Exception e) {
                viewToast(this, "No Bus Available");
            }
        });

    }

    private void paginationFooter() {
        int val = listSize % pageSize;
        val = val == 0 ? 0 : 1;
        noOfPages = listSize / pageSize + val;

        LinearLayout ll = findViewById(R.id.pageNumberContainer);
        btns = new Button[noOfPages];
        if (noOfPages <= 6) {
            ((FrameLayout.LayoutParams) ll.getLayoutParams()).gravity = Gravity.CENTER;
        }

        for (int i = 0; i < noOfPages; i++) {
            btns[i] = new Button(this);
            btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btns[i].setText("" + (i + 1));
            btns[i].setTextSize(10);
            btns[i].setTextColor(getResources().getColor(android.R.color.black));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100, 100);
            ll.addView(btns[i], lp);
            final int j = i;
            btns[j].setOnClickListener(v -> {
                currentPage = j;
                goToPage(j);
            });
        }
    }

    private void goToPage(int index) {
        for (int i = 0; i < noOfPages; i++) {
            if (i == index) {
                btns[i].setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                btns[i].setTextColor(getResources().getColor(android.R.color.white));
                scrollToItem(btns[index]);
                viewPaginatedList(listBus, currentPage);
            } else {
                btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btns[i].setTextColor(getResources().getColor(android.R.color.black));
            }
        }
    }

    private void viewPaginatedList(List<Bus> listBus, int page) {
        int start = page * pageSize;
        int end = Math.min(start + pageSize, listSize);
        List<Bus> subList = listBus.subList(start, end);
        busArrayAdapter = new BusArrayAdapter(this, subList);
        listView.setAdapter(busArrayAdapter);
    }

    private void scrollToItem(Button btn) {
        int scrollX = (btn.getLeft() - (pageScroll.getWidth() / 2)) + (btn.getWidth() / 2);
        pageScroll.smoothScrollTo(scrollX, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        searchItem = menu.findItem(R.id.search_icon);
        profileItem = menu.findItem(R.id.profile_icon);
        paymentItem = menu.findItem(R.id.payment_icon);
        profileItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this, AboutMeActivity.class);
                finish();
                startActivity(intent);
                return true;
            }
        });

        paymentItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this, HistoryListActivity.class);
                finish();
                startActivity(intent);
                return true;
            }
        });
        return true;
    }

    protected void getBusList() {
        mApiService.getAllBus().enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                listBus = response.body();
                StoredBus.storedBus = listBus;
                OrderHistory.buses = listBus;
                listSize = listBus.size();
                busArrayAdapter = new BusArrayAdapter(MainActivity.this, listBus);
                listView.setAdapter(busArrayAdapter);
            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {

            }
        });
    }

    protected void getAllPayment() {
        mApiService.getAllPayment().enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                OrderHistory.payments = response.body();
                OrderHistory.payments.forEach(payment -> {
                    if (payment.buyerId == LoggedAccount.loggedAccount.id) {
                        OrderHistory.accountPayments.add(payment);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {

            }
        });
    }

    private void viewToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    protected void updateBalance() {
        mApiService.getAccountDetails(LoggedAccount.loggedAccount.email, LoggedAccount.loggedAccount.password).enqueue(new Callback<BaseResponse<Account>>() {
            @Override
            public void onResponse(Call<BaseResponse<Account>> call, Response<BaseResponse<Account>> response) {
                if (response.isSuccessful()) {
                    LoggedAccount.loggedAccount = response.body().payload;
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Account>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        System.out.println("DESTROYED");
        super.onDestroy();
    }
}