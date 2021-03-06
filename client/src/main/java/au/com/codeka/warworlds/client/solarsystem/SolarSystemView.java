package au.com.codeka.warworlds.client.solarsystem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import au.com.codeka.warworlds.client.util.ViewBackgroundGenerator;
import au.com.codeka.warworlds.client.world.ImageHelper;
import au.com.codeka.warworlds.common.Log;
import au.com.codeka.warworlds.common.Vector2;
import au.com.codeka.warworlds.common.proto.Building;
import au.com.codeka.warworlds.common.proto.Planet;
import au.com.codeka.warworlds.common.proto.Star;

/**
 * The view which displays the star and planets.
 */
public class SolarSystemView extends RelativeLayout {
  private static final Log log = new Log("SolarSystemView");
  private final Paint orbitPaint;
  private Star star;
  private PlanetInfo[] planetInfos;

  public SolarSystemView(Context context, AttributeSet attrs) {
    super(context, attrs);
    ViewBackgroundGenerator.setBackground(this, onBackgroundDrawHandler);

    orbitPaint = new Paint();
    orbitPaint.setARGB(255, 255, 255, 255);
    orbitPaint.setStyle(Paint.Style.STROKE);
  }

  public void setStar(Star star) {
    this.star = star;
    planetInfos = new PlanetInfo[star.planets.size()];
    for (int i = 0; i < star.planets.size(); i++) {
      PlanetInfo planetInfo = new PlanetInfo();
      planetInfo.planet = star.planets.get(i);
      planetInfo.centre = new Vector2(0, 0);
      planetInfo.distanceFromSun = 0.0f;
      planetInfos[i] = planetInfo;
    }

    ImageView sunImageView = new ImageView(getContext());

    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
        (int)(256 * getContext().getResources().getDisplayMetrics().density),
        (int)(256 * getContext().getResources().getDisplayMetrics().density));
    lp.topMargin = -lp.height / 2;
    lp.leftMargin = -lp.width / 2;
    sunImageView.setLayoutParams(lp);
    addView(sunImageView);
    Picasso.with(getContext())
        .load(ImageHelper.getStarImageUrl(getContext(), star, 256, 256))
        .into(sunImageView);

    placePlanets();
  }

  private float getDistanceFromSun(int planetIndex) {
    int width = getWidth();
    if (width == 0) {
      return 0.0f;
    }

    width -= (int)(16 * getContext().getResources().getDisplayMetrics().density);
    float planetStart = 150 * getContext().getResources().getDisplayMetrics().density;
    float distanceBetweenPlanets = width - planetStart;
    distanceBetweenPlanets /= planetInfos.length;
    return planetStart + (distanceBetweenPlanets * planetIndex) + (distanceBetweenPlanets / 2.0f);
  }

  private void placePlanets() {
    if (planetInfos == null) {
      return;
    }
    int width = getWidth();
    if (width == 0) {
      this.post(new Runnable() {
        @Override
        public void run() {
          placePlanets();
        }
      });
      return;
    }

    for (int i = 0; i < planetInfos.length; i++) {
      PlanetInfo planetInfo = planetInfos[i];

      float distanceFromSun = getDistanceFromSun(i);
      float x = distanceFromSun;
      float y = 0;

      float angle = (0.5f/(planetInfos.length + 1));
      angle = (float) ((angle*(planetInfos.length - i - 1)*Math.PI) + angle*Math.PI);

      Vector2 centre = new Vector2(x, y);
      centre.rotate(angle);

      planetInfo.centre = centre;
      planetInfo.distanceFromSun = distanceFromSun;
      planetInfo.imageView = new ImageView(getContext());

      RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
          (int)(64 * getContext().getResources().getDisplayMetrics().density),
          (int)(64 * getContext().getResources().getDisplayMetrics().density));
      lp.topMargin = (int) centre.y - (lp.height / 2);
      lp.leftMargin = (int) centre.x - (lp.width / 2);
      planetInfo.imageView.setLayoutParams(lp);
      addView(planetInfo.imageView);

      Picasso.with(getContext())
          .load(ImageHelper.getPlanetImageUrl(getContext(), star, i, 64, 64))
          .into(planetInfo.imageView);

      if (planetInfo.planet.colony != null) {
        for (Building building : planetInfo.planet.colony.buildings) {
        //  BuildingDesign design = (BuildingDesign) DesignManager.i.getDesign(DesignKind.BUILDING, building.getDesignID());
        //  if (design.showInSolarSystem()) {
        //    planetInfo.buildings.add((Building) building);
        //  }
        }
      }

      //if (!planetInfo.buildings.isEmpty()) {
      //  Collections.sort(planetInfo.buildings, mBuildingDesignComparator);
      //}
    }

    updateSelection();
  }

  private void updateSelection() {
    //if (mSelectedPlanet != null && mSelectionView != null) {
    //  mSelectionView.setVisibility(View.VISIBLE);

    //  RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mSelectionView.getLayoutParams();
    //  params.width = (int) ((((mSelectedPlanet.planet.getSize() - 10.0) / 8.0) + 4.0) * 10.0) + (int) (40 * getPixelScale());
    //  params.height = params.width;
    //  params.leftMargin = (int) (getLeft() + mSelectedPlanet.centre.x - (params.width / 2));
    //  params.topMargin = (int) (getTop() + mSelectedPlanet.centre.y - (params.height / 2));
    //  mSelectionView.setLayoutParams(params);
    //}
  }

  private final ViewBackgroundGenerator.OnDrawHandler onBackgroundDrawHandler =
      new ViewBackgroundGenerator.OnDrawHandler() {
    @Override
    public void onDraw(Canvas canvas) {
      for (int i = 0; i < planetInfos.length; i++) {
        float radius = getDistanceFromSun(i);
        canvas.drawCircle(0.0f, 0.0f, radius, orbitPaint);
      }
    }
  };

  /** This class contains info about the planets we need to render and interact with. */
  private static class PlanetInfo {
    public Planet planet;
    public Vector2 centre;
    public float distanceFromSun;
    public ImageView imageView;
  }
}
