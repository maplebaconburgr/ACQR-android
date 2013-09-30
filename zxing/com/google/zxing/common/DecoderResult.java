/*
 * Copyright 2007 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.zxing.common;

import java.util.List;

import com.google.zxing.qrcode.decoder.Mode;
import com.google.zxing.qrcode.decoder.Version;

/**
 * <p>Encapsulates the result of decoding a matrix of bits. This typically
 * applies to 2D barcode formats. For now it contains the raw bytes obtained,
 * as well as a String interpretation of those bytes, if applicable.</p>
 *
 * @author Sean Owen
 */
public final class DecoderResult {

  private final byte[] rawBytes;
  private final String text;
  private final List<byte[]> byteSegments;
  private final String ecLevel;
  private Integer errorsCorrected;
  private Integer erasures;
  private Object other;
  private Version version;
  private Mode mode;
  private int count;

  public DecoderResult(byte[] rawBytes,
                       String text,
                       List<byte[]> byteSegments,
                       String ecLevel){
	  this(rawBytes, text, byteSegments, ecLevel, null, null, -1);
  }
  
  public DecoderResult(byte[] rawBytes,
                       String text,
                       List<byte[]> byteSegments,
                       String ecLevel, Version version, Mode mode, int count) {
    this.rawBytes = rawBytes;
    this.text = text;
    this.byteSegments = byteSegments;
    this.ecLevel = ecLevel;
    this.setVersion(version);
    this.setMode(mode);
    this.setCount(count);
  }

  public byte[] getRawBytes() {
    return rawBytes;
  }

  public String getText() {
    return text;
  }

  public List<byte[]> getByteSegments() {
    return byteSegments;
  }

  public String getECLevel() {
    return ecLevel;
  }

  public Integer getErrorsCorrected() {
    return errorsCorrected;
  }

  public void setErrorsCorrected(Integer errorsCorrected) {
    this.errorsCorrected = errorsCorrected;
  }

  public Integer getErasures() {
    return erasures;
  }

  public void setErasures(Integer erasures) {
    this.erasures = erasures;
  }
  
  public Object getOther() {
    return other;
  }

  public void setOther(Object other) {
    this.other = other;
  }

public Mode getMode() {
	return mode;
}

public void setMode(Mode mode) {
	this.mode = mode;
}

public Version getVersion() {
	return version;
}

public void setVersion(Version version) {
	this.version = version;
}

public int getCount() {
	return count;
}

public void setCount(int count) {
	this.count = count;
}
  
}