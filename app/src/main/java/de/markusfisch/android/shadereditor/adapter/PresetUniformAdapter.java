package de.markusfisch.android.shadereditor.adapter;

import de.markusfisch.android.shadereditor.R;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Locale;

public class PresetUniformAdapter extends BaseAdapter {
	public static final class Uniform {
		public final String type;
		public final String name;
		public final String rationale;
		public final int minSdk;

		public boolean isAvailable() {
			return minSdk <= Build.VERSION.SDK_INT;
		}

		public boolean isSampler() {
			return type.startsWith("sampler");
		}

		private Uniform(String type, String name, String rationale) {
			this(type, name, rationale, 0);
		}

		private Uniform(
				String type,
				String name,
				String rationale,
				int minSdk) {
			this.type = type;
			this.name = name;
			this.rationale = rationale;
			this.minSdk = minSdk;
		}
	}

	private final String uniformFormat;
	private final Uniform uniforms[];

	public PresetUniformAdapter(Context context) {
		uniformFormat = context.getString(R.string.uniform_format);
		uniforms = new Uniform[]{
				new Uniform(
						"sampler2D",
						"backbuffer",
						context.getString(R.string.previous_frame)),
				new Uniform(
						"float",
						"battery",
						context.getString(R.string.battery_level)),
				new Uniform(
						"samplerExternalOES",
						"camera_back",
						context.getString(R.string.camera_back),
						Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1),
				new Uniform(
						"samplerExternalOES",
						"camera_front",
						context.getString(R.string.camera_front),
						Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1),
				new Uniform(
						"vec4",
						"date",
						context.getString(R.string.date_time)),
				new Uniform(
						"float",
						"ftime",
						context.getString(R.string.time_in_cycle)),
				new Uniform(
						"vec3",
						"gravity",
						context.getString(R.string.gravity_vector)),
				new Uniform(
						"float",
						"light",
						context.getString(R.string.light)),
				new Uniform(
						"vec3",
						"linear",
						context.getString(R.string.linear_acceleration_vector)),
				new Uniform(
						"vec3",
						"magnetic",
						context.getString(R.string.magnetic_field)),
				new Uniform(
						"vec2",
						"offset",
						context.getString(R.string.wallpaper_offset)),
				new Uniform(
						"vec3",
						"orientation",
						context.getString(R.string.device_orientation)),
				new Uniform(
						"vec3",
						"pointers[10]",
						context.getString(R.string.positions_of_touches)),
				new Uniform(
						"int",
						"pointerCount",
						context.getString(R.string.number_of_touches)),
				new Uniform(
						"float",
						"pressure",
						context.getString(R.string.pressure)),
				new Uniform(
						"float",
						"proximity",
						context.getString(R.string.proximity)),
				new Uniform(
						"vec2",
						"resolution",
						context.getString(R.string.resolution_in_pixels)),
				new Uniform(
						"vec3",
						"rotation",
						context.getString(R.string.device_rotation)),
				new Uniform(
						"int",
						"second",
						context.getString(R.string.int_seconds_since_load)),
				new Uniform(
						"float",
						"startRandom",
						context.getString(R.string.start_random)),
				new Uniform(
						"float",
						"subsecond",
						context.getString(R.string.fractional_part_of_seconds_since_load)),
				new Uniform(
						"float",
						"time",
						context.getString(R.string.time_in_seconds_since_load)),
				new Uniform(
						"vec2",
						"touch",
						context.getString(R.string.touch_position_in_pixels)),
		};
	}

	@Override
	public int getCount() {
		return uniforms.length;
	}

	@Override
	public Uniform getItem(int position) {
		return uniforms[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(
			int position,
			View convertView,
			ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater
					.from(parent.getContext())
					.inflate(R.layout.row_preset, parent, false);
		}

		ViewHolder holder = getViewHolder(convertView);
		Uniform uniform = uniforms[position];
		boolean enabled = uniform.isAvailable();

		convertView.setEnabled(enabled);

		holder.name.setTextColor(ContextCompat.getColor(
				parent.getContext(),
				enabled ?
						android.R.color.primary_text_dark :
						R.color.disabled_text));
		holder.name.setText(String.format(
				Locale.US,
				uniformFormat,
				uniform.name,
				uniform.type));
		holder.rationale.setText(uniform.rationale);

		return convertView;
	}

	private ViewHolder getViewHolder(View view) {
		ViewHolder holder;
		if ((holder = (ViewHolder) view.getTag()) == null) {
			holder = new ViewHolder();
			holder.name = (TextView) view.findViewById(R.id.name);
			holder.rationale = (TextView) view.findViewById(R.id.rationale);
			view.setTag(holder);
		}

		return holder;
	}

	private static final class ViewHolder {
		private TextView name;
		private TextView rationale;
	}
}
