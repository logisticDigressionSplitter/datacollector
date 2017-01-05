/**
 * Copyright 2015 StreamSets Inc.
 *
 * Licensed under the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.streamsets.datacollector.runner;

import java.util.Map;

/**
 * Offset tracker - for committing and retrieving offsets in a store.
 */
public interface SourceOffsetTracker {

  /**
   * Return if the source finished processing data.
   *
   * This is more of a historical method as it determines whether source is done reading by checking for special
   * offset value. This method will only work (e.g. return true) for (Pull)Source - it will never return true
   * for PushSource.
   */
  public boolean isFinished();

  /**
   * Return one dimensional offset.
   *
   * Applicable only for (Pull)Source. This method is kept for backward compatibility and will be removed in the future.
   */
  @Deprecated
  public String getOffset();

  /**
   * Set one dimensional offset.
   *
   * Applicable only for (Pull)Source. This method is kept for backward compatibility and will be removed in the future.
   */
  @Deprecated
  public void setOffset(String newOffset);

  /**
   * Commit currently staged offsets to persistent store.
   */
  @Deprecated
  public void commitOffset();

  /**
   * Change offset for entity in the tracked offsets map and commit it to persistent store.
   *
   * @param entity Entity to be changed, null will disable changing the staged object (making this equivalent to commitOffsets() call)
   * @param newOffset New offset for given entity, null will remove the entity from tracking map
   */
  public void commitOffset(String entity, String newOffset);

  /**
   * Return currently staged offsets map.
   *
   * This method should return immutable version of the offsets map - thus changes to the returned map won't be
   * reflected. Use methods on this interface to mutate the state.
   */
  public Map<String, String> getOffsets();

  /**
   * Get time of lastly committed batch.
   */
  public long getLastBatchTime();

}
