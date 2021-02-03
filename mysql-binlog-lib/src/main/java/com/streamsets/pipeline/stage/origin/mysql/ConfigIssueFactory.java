/*
 * Copyright 2021 StreamSets Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.streamsets.pipeline.stage.origin.mysql;

import com.streamsets.pipeline.api.ConfigIssue;
import com.streamsets.pipeline.api.Source.Context;

public class ConfigIssueFactory {
  private final Context context;

  public ConfigIssueFactory(final Context context) {
    this.context = context;
  }

  public ConfigIssue create(
      final String name,
      final String config,
      final Errors error,
      final Object... args
  ) {
    return context.createConfigIssue(name, config, error, args);
  }
}
