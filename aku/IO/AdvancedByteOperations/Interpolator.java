/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aku.IO.AdvancedByteOperations;

/**
 *
 * @author Von Bock
 */
public class Interpolator {
  
  public static class CubicInterpolator
  {
          public static float getValue (float[] p, float x) {
                  return p[1] + 0.5f * x*(p[2] - p[0] + x*(2.0f*p[0] - 5.0f*p[1] + 4.0f*p[2] - p[3] + x*(3.0f*(p[1] - p[2]) + p[3] - p[0])));
          }
  }

  public static class BicubicInterpolator extends CubicInterpolator
  {
          private static float[] arr = new float[4];

          public static float getValueBi (float[][] p, float x, float y) {
                  arr[0] = getValue(p[0], y);
                  arr[1] = getValue(p[1], y);
                  arr[2] = getValue(p[2], y);
                  arr[3] = getValue(p[3], y);
                  return getValue(arr, x);
          }
  }

}
