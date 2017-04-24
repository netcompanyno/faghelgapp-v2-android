package no.mesan.faghelg.view.program;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import no.mesan.faghelg.injector.components.AppComponent;
import no.mesan.faghelg.model.Event;
import no.mesan.faghelg.model.Person;
import no.mesan.faghelg.view.BaseFragment;
import no.mesan.faghelg.view.people.PeopleItemView;
import no.mesan.faghelgapps.R;

public class EventOverlayFragment extends BaseFragment {

    @Bind(R.id.event_title)
    TextView txtTitle;

    @Bind(R.id.event_timestamp)
    TextView txtTimestamp;

    @Bind(R.id.imgEvent)
    ImageView imgEvent;

    @Bind(R.id.layoutSpeakers)
    LinearLayout layoutSpeakers;

    @Bind(R.id.txtDescription)
    TextView txtDescription;

    @Bind(R.id.txtPosition)
    TextView txtPosition;

    private int eventDayPosition = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        Event event = getArguments().getParcelable(EventPagerAdapter.ARGS_EVENT);

        updatePagerView();
        updateViews(event);

        return view;
    }

    private void updatePagerView() {
        int numberOfEvents = getArguments().getInt(EventPagerAdapter.ARGS_NUMBER_OF_EVENTS);
        eventDayPosition = getArguments().getInt(EventPagerAdapter.ARGS_EVENT_DAY_POSITION);
        txtPosition.setText(getString(R.string.position, eventDayPosition, numberOfEvents));
    }

    @Override
    protected int getContentViewId() {
        return R.layout.program_item_overlay;
    }

    @Override
    protected void inject(AppComponent appComponent) {

    }

    @OnClick(R.id.btnClose)
    public void onClick() {
        onClose();
    }

    private void onClose() {
        Intent data = new Intent();
        data.putExtra(EventPagerAdapter.ARGS_SCROLLED_EVENT_POSITION, eventDayPosition - 1);
        getActivity().setResult(Activity.RESULT_OK, data);
        getActivity().finish();
    }

    private void updateViews(Event event) {
        updateEventViews(event);

        if (event.getSpeakers() != null) {
            updateResponsibleViews(event);
        }
    }

    private void updateEventViews(Event event) {
        txtTitle.setText(event.getTitle());
        txtTimestamp.setText(event.getStartTime().toString("HH:mm"));
        txtDescription.setText(event.getDescription());
        //txtHosts.setText(event.getHostNames());
        Picasso.with(getApplicationContext()).load(event.getEventImageUrl()).into(imgEvent);
    }
    private void updateResponsibleViews(Event event) {
        List<Person> speakers = event.getSpeakers();

        for(Person speaker : speakers) {

            PeopleItemView speakerView = new PeopleItemView(getContext());
            speakerView.bindTo(speaker);
            layoutSpeakers.addView(speakerView);

        }

        if(event.get)
    }

    public void onBackPressed() {
        onClose();
    }
}
