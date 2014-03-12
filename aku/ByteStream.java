/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aku;

import com.google.common.primitives.Bytes;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Von Bock
 */
public class ByteStream {
  
  protected List<Byte> buffer = new LinkedList();
  protected byte[] bufferAsArray;
  protected int offset = 0;
  protected int opcodeStart;

  public ByteStream() {
  }

  public void addByte(int value) {
    addByte(value, this.offset++);
  }

  public void addByte(int value, int pos) {
    if (pos < this.buffer.size()) {
      this.buffer.set(pos, Byte.valueOf((byte) value));
    } else {
      this.buffer.add(Byte.valueOf((byte) value));
    }
  }

  public void addBytes(byte[] bytes) {
    this.buffer.addAll(Bytes.asList(bytes));
    this.offset += bytes.length;
  }

  public void addBytes(byte[] bytes, int i, int size) {
    /* 111 */
    this.buffer.addAll(Bytes.asList(bytes).subList(i, i + size));
    /* 112 */
    this.offset += size;
  }

  public byte[] buffer() {
    /* 298 */
    return Bytes.toArray(this.buffer);
  }

  public List<Byte> getBuffer() {
    /* 302 */
    return this.buffer;
  }

  public int getByte() {
    if (bufferAsArray == null) {
      bufferAsArray = Bytes.toArray(buffer);
    }
    return open() > 0 ? bufferAsArray[offset++] : 0;
  }

  public int offset() {
    /* 310 */
    return this.offset;
  }

  void resetBuffer() {
    /* 323 */
    this.buffer.clear();
    /* 324 */
    setOffset(0);
  }

  public void setOffset(int i) {
    /* 314 */
    this.offset = i;
  }

  public byte[] subBuffer(int start, int end) {
    /* 306 */
    return Bytes.toArray(this.buffer.subList(start, end));
  }
  
   private int open() {
/* 294 */     return this.offset < this.buffer.size() ? 1 : 0;
   }
  
}
