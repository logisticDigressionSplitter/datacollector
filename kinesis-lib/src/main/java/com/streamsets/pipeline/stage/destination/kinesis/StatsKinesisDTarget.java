/*
 * Copyright 2017 StreamSets Inc.
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
package com.streamsets.pipeline.stage.destination.kinesis;

import com.streamsets.pipeline.api.ConfigDefBean;
import com.streamsets.pipeline.api.ConfigGroups;
import com.streamsets.pipeline.api.GenerateResourceBundle;
import com.streamsets.pipeline.api.HideConfigs;
import com.streamsets.pipeline.api.HideStage;
import com.streamsets.pipeline.api.StageDef;
import com.streamsets.pipeline.api.StatsAggregatorStage;
import com.streamsets.pipeline.api.Target;
import com.streamsets.pipeline.api.base.configurablestage.DTarget;
import com.streamsets.pipeline.config.DataFormat;
import com.streamsets.pipeline.stage.destination.lib.ToOriginResponseConfig;

@StageDef(
    version = 10,
    label = "Write to Kinesis",
    description = "Writes Pipeline Statistic records to Kinesis",
    icon = "",
    upgrader = KinesisTargetUpgrader.class,
    upgraderDef = "upgrader/StatsKinesisDTarget.yaml",
    onlineHelpRefUrl = ""
)
@ConfigGroups(value = Groups.class)
@StatsAggregatorStage
@HideStage(HideStage.Type.STATS_AGGREGATOR_STAGE)
@HideConfigs(
  preconditions = true,
  onErrorRecord = true,
  value = {
      "kinesisConfig.dataFormat",
      "kinesisConfig.preserveOrdering",
      "kinesisConfig.connection.proxyConfig.connectionTimeout",
      "kinesisConfig.connection.proxyConfig.socketTimeout",
      "kinesisConfig.connection.proxyConfig.retryCount",
      "kinesisConfig.connection.proxyConfig.useProxy"
  }
)
@GenerateResourceBundle
public class StatsKinesisDTarget extends DTarget {

  @ConfigDefBean(groups = {"KINESIS", "DATA_FORMAT"})
  public KinesisProducerConfigBean kinesisConfig;

  @Override
  protected Target createTarget() {
    kinesisConfig.dataFormat = DataFormat.SDC_JSON;
    kinesisConfig.preserveOrdering = true;
    return new KinesisTarget(kinesisConfig, new ToOriginResponseConfig());
  }
}
