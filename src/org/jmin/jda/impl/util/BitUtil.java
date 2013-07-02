/*
 *Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.util;

/**
 * 
 * 
 * @author Chris Liao
 */
public class BitUtil {

	public final static byte[] shortToByte(short v) {
		byte[] data = new byte[2];
		data[0] = (byte) ((v >>> 0) & 0xff);
		data[1] = (byte) ((v >>> 8) & 0xff);
		return data;
	}

	public static short byteToShort(byte[] data) {
		return (short) (((data[1] & 0xff) << 8) | (data[0] & 0xff));
	}

	public static byte[] intToByte(int v) {
		byte[] data = new byte[4];
		data[0] = (byte) ((v >>> 0) & 0xff);
		data[1] = (byte) ((v >>> 8) & 0xff);
		data[2] = (byte) ((v >>> 16) & 0xff);
		data[3] = (byte) ((v >>> 24) & 0xff);
		return data;
	}

	public static int byteToInt(byte data[]) {
		return (data[0] & 0xff) | ((data[1] & 0xff) << 8)
				| ((data[2] & 0xff) << 16) | ((data[3] & 0xff) << 24);
	}

	public static long byteToLong(byte[] data) {
		return ((((long) data[0]) & 0xffL) << 56)
				| ((((long) data[1]) & 0xffL) << 48)
				| ((((long) data[2]) & 0xffL) << 40)
				| ((((long) data[3]) & 0xffL) << 32)
				| ((((long) data[4]) & 0xffL) << 24)
				| ((((long) data[5]) & 0xffL) << 16)
				| ((((long) data[6]) & 0xffL) << 8) | (((long) data[7]) & 0xffL);
	}

	public static byte[] longToByte(long v) {
		byte[] data = new byte[8];
		data[7] = (byte) ((v >>> 0) & 0xff);
		data[6] = (byte) ((v >>> 8) & 0xff);
		data[5] = (byte) ((v >>> 16) & 0xff);
		data[4] = (byte) ((v >>> 24) & 0xff);
		data[3] = (byte) ((v >>> 32) & 0xff);
		data[2] = (byte) ((v >>> 40) & 0xff);
		data[1] = (byte) ((v >>> 48) & 0xff);
		data[0] = (byte) ((v >>> 56) & 0xff);
		return data;
	}

	public static byte[] floatToByte(float d) {
		byte[] bytes = new byte[4];
		int l = Float.floatToIntBits(d);
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = new Integer(l).byteValue();
			l = l >> 8;
		}
		return bytes;
	}

	public static float byteToFloat(byte[] b) {
		int l;
		l = b[0];
		l &= 0xff;
		l |= ((long) b[1] << 8);
		l &= 0xffff;
		l |= ((long) b[2] << 16);
		l &= 0xffffff;
		l |= ((long) b[3] << 24);
		l &= 0xffffffffl;

		return Float.intBitsToFloat(l);
	}

	public static byte[] doubleToByte(double d) {
		byte[] bytes = new byte[8];
		long l = Double.doubleToLongBits(d);
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = new Long(l).byteValue();
			l = l >> 8;
		}
		return bytes;
	}

	public static double byteToDouble(byte[] b) {
		long l;
		l = b[0];
		l &= 0xff;
		l |= ((long) b[1] << 8);
		l &= 0xffff;
		l |= ((long) b[2] << 16);
		l &= 0xffffff;
		l |= ((long) b[3] << 24);
		l &= 0xffffffffl;
		l |= ((long) b[4] << 32);
		l &= 0xffffffffffl;

		l |= ((long) b[5] << 40);
		l &= 0xffffffffffffl;
		l |= ((long) b[6] << 48);
		l &= 0xffffffffffffffl;

		l |= ((long) b[7] << 56);
		return Double.longBitsToDouble(l);
	}

	public static void main(String[] args) {
		System.out.println(byteToShort(shortToByte((short) 10)));
		System.out.println(byteToInt(intToByte((int) 10)));
		System.out.println(byteToLong(longToByte((long) 10)));
		System.out.println(byteToFloat(floatToByte((float) 10.30)));
		System.out.println(byteToDouble(doubleToByte(10.20)));
	}
}
