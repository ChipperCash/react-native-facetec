package com.reactnativefacetec.Processors;

public abstract class Processor {
  public abstract boolean isSuccess();
  public void updateLoadingUI(boolean success) {
  }
}

