package com.tatsun.lib.gm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;


public class Misc {
	
	private static final PointFComparator pointFComparator = new PointFComparator();
	
	private Misc() {
		// not instanciate
	}

	/**
	 * 
	 * @param max(exclusive)
	 * @return
	 */
	public static int getRand(int max) {
		return (int)(Math.random() * max);
	}
	
	/**
	 * 
	 * @param max(exclusive)
	 * @return
	 */
	public static float getRand(float max) {
		return (float)(Math.random() * max);
	}
	
	public static Object getRand(Object[] objects) {
		return objects[getRand(objects.length)];
	}

	public static int getRand(int[] array) {
		return array[getRand(array.length)];
	}

	public static int[] getRand(int[][] array) {
		return array[getRand(array.length)];
	}

	public static float getRand(float[] array) {
		return array[getRand(array.length)];
	}

	public static float[] getRand(float[][] array) {
		return array[getRand(array.length)];
	}

	public static double getRand(double[] array) {
		return array[getRand(array.length)];
	}

	public static double[] getRand(double[][] array) {
		return array[getRand(array.length)];
	}

	public static String getRand(String[] array) {
		return array[getRand(array.length)];
	}

	public static String[] getRand(String[][] array) {
		return array[getRand(array.length)];
	}

	public static Point getRand(Point[] array) {
		return array[getRand(array.length)];
	}

	public static Point[] getRand(Point[][] array) {
		return array[getRand(array.length)];
	}

	public static PointF getRand(PointF[] array) {
		return array[getRand(array.length)];
	}

	public static PointF[] getRand(PointF[][] array) {
		return array[getRand(array.length)];
	}
	
	public static Rect getRand(Rect[] array) {
		return array[getRand(array.length)];
	}

	public static Rect[] getRand(Rect[][] array) {
		return array[getRand(array.length)];
	}
	
	public static RectF getRand(RectF[] array) {
		return array[getRand(array.length)];
	}

	public static RectF[] getRand(RectF[][] array) {
		return array[getRand(array.length)];
	}
	
	/**
	 * 
	 * @param min(inclusive)
	 * @param max(exclusive)
	 * @return
	 */
	public static int getRangeRand(int min, int max) {
		return (int)(Math.random() * (max - min)) + min;
	}

	/**
	 * 
	 * @param min(inclusive)
	 * @param max(exclusive)
	 * @return
	 */
	public static float getRangeRand(float min, float max) {
		return (float)(Math.random() * (max - min)) + min;
	}

	/**
	 * ratio is larger than random(max 100) ? 
	 * @param ratio
	 * @return
	 */
	public static boolean isInRatio(int ratio) {
		return Math.random() * 100 <= (double)ratio;
	}

	/**
	 * ratio is larger than random(max 1) ?
	 * @param ratio
	 * @return
	 */
	public static boolean isInRatio(float ratio) {
		return Math.random() < (double)ratio;
	}
	
	
	public static boolean checkArrayRange(Object[] array, int col) {
		if(col < 0 || col >= array.length)
			return false;
		return true;
	}
	
	public static boolean checkArrayRange(Object[][] array, int col, int row) {
		if(col < 0 || row < 0 || row >= array.length || array.length == 0)
			return false;
		if(col >= array[0].length)
			return false;
		return true;
	}
	
	public static boolean checkFlg(int lhs, int key) {
		return 0 != (lhs & key);
	}
	
	public static int setFlg(int lhs, int key) {
		return lhs | key;
	}
	
	public static int setFlg(int lhs, int key, int clearKey) {
		return lhs ^ clearKey | key;
	}
	
	public static int clearFlg(int lhs, int key) {
		return lhs ^ key;
	}
	
	public static final String repeatCharactor(String charactor, int repeatCount) {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < repeatCount; ++i) {
			sb.append(charactor);
		}
		
		return sb.toString();
	}
	
	public static final PointF[] sortPoints(PointF[] points) {
		Arrays.sort(points, pointFComparator);
		return points;
	}
	
	public static class PointFComparator implements Comparator<PointF> {

		@Override
		public int compare(PointF l, PointF r) {
			float ly = l.y;
			float ry = r.y;
			if(ly < ry) {
				return -1;
			} else if(ly > ry) {
				return 1;
			} else {
				float lx = l.x;
				float rx = r.x;
				if(lx < rx) {
					return -1;
				} else if(lx > rx) {
					return 1;
				} else {
					return 0;
				}
			}
		}
		
	}
	
	/**
	 * single characters(0x20 ～ 0x7F & 0x0A, 0x0D)
	 * @param code
	 * @return
	 */
	public static boolean isSingleCharacter(int code) {
		if ((code >= 0x20 && code <= 0x7F ) ||
		     code == 0x0D || code == 0x0A ) {
			return true;
		}
		return false;
	}
	
	public static int calcCharactorSize(String source) {
		if(source == null || source.length() == 0)
			return 0;
		int length = 0;
        for(int i = 0 ; i < source.length() ; i++){
            char c = source.charAt(i);
            if (isSingleCharacter(c)) {
                length++;
            }else{
                length += 2;
            }
        }
        return length;
	}
	
	public static String[] folding(String source, int maxLength, int maxLines) {
		ArrayList<String> ret = new ArrayList<String>(maxLines);
		maxLength *= 2;
        if(source != null && source.length() > 0){
            int num = source.length();
            StringBuilder buf = new StringBuilder();
            int length = 0;
            for(int i = 0 ; i < num ; i++){
                char c = source.charAt(i);
                if (isSingleCharacter(c)) {
                    length++;
                }else{
                    length += 2;
                }
                buf.append(c);
                
                if(length >= maxLength){
                	if(ret.size() >= maxLines) {
                		break;
                	}
                    ret.add(buf.toString());
                    buf = new StringBuilder();
                    length = 0;
                }
            }
            if(length != 0 &&
            		ret.size() < maxLines) {
                ret.add(buf.toString());
                buf = new StringBuilder();
                length = 0;
            }
        }
        return ret.toArray(new String[0]);
    }
}
