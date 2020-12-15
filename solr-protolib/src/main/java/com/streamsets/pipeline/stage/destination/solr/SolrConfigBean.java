/*
 * Copyright 2019 StreamSets Inc.
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

package com.streamsets.pipeline.stage.destination.solr;


import com.streamsets.pipeline.api.ConfigDef;
import com.streamsets.pipeline.api.Dependency;
import com.streamsets.pipeline.api.ListBeanModel;
import com.streamsets.pipeline.api.ValueChooserModel;
import com.streamsets.pipeline.stage.processor.scripting.ProcessingMode;
import com.streamsets.pipeline.stage.processor.scripting.ProcessingModeChooserValues;
import com.streamsets.pipeline.api.credential.CredentialValue;

import java.util.List;

public class SolrConfigBean {

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.MODEL,
      defaultValue = "SINGLE_NODE",
      label = "Instance Type",
      description = "",
      displayPosition = 10,
      displayMode = ConfigDef.DisplayMode.BASIC,
      group = "SOLR"
  )
  @ValueChooserModel(InstanceTypeOptionsChooserValues.class)
  public InstanceTypeOptions instanceType;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.STRING,
      defaultValue = "http://localhost:8983/solr/corename",
      label = "Solr URI",
      description = "",
      displayPosition = 20,
      displayMode = ConfigDef.DisplayMode.BASIC,
      group = "SOLR",
      dependsOn = "instanceType",
      triggeredByValue = { "SINGLE_NODE"}
  )
  public String solrURI;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.STRING,
      defaultValue = "localhost:9983",
      label = "ZooKeeper Connection String",
      description = "Comma-separated list of the Zookeeper <HOST>:<PORT> used by the SolrCloud",
      displayPosition = 30,
      displayMode = ConfigDef.DisplayMode.BASIC,
      group = "SOLR",
      dependsOn = "instanceType",
      triggeredByValue = { "SOLR_CLOUD"}
  )
  public String zookeeperConnect;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.STRING,
      defaultValue = "",
      label = "Default Collection Name",
      description = "",
      displayPosition = 30,
      displayMode = ConfigDef.DisplayMode.BASIC,
      group = "SOLR",
      dependsOn = "instanceType",
      triggeredByValue = { "SOLR_CLOUD"}
  )
  public String defaultCollection;


  @ConfigDef(
      required = true,
      type = ConfigDef.Type.MODEL,
      defaultValue = "NONE",
      label = "Authentication type",
      description = "Authentication type used to connect to Solr",
      displayPosition = 35,
      displayMode = ConfigDef.DisplayMode.BASIC,
      group = "SOLR"
  )
  @ValueChooserModel(AuthTypeOptionsChooserValues.class)
  public AuthTypeOptions authType;


  @ConfigDef(
      displayMode = ConfigDef.DisplayMode.BASIC,
      required = true,
      type = ConfigDef.Type.CREDENTIAL,
      label = "Username",
      displayPosition = 20,
      group = "CREDENTIALS",
      dependencies = {
          @Dependency(configName = "authType", triggeredByValues = "BASIC_AUTH")
      }
  )
  public CredentialValue username;

  @ConfigDef(
      displayMode = ConfigDef.DisplayMode.BASIC,
      required = true,
      type = ConfigDef.Type.CREDENTIAL,
      label = "Password",
      displayPosition = 25,
      group = "CREDENTIALS",
      dependencies = {
          @Dependency(configName = "authType", triggeredByValues = "BASIC_AUTH")
      }
  )
  public CredentialValue password;


  @ConfigDef(
      required = true,
      type = ConfigDef.Type.MODEL,
      defaultValue = "BATCH",
      label = "Record Indexing Mode",
      description = "If 'Record by Record' the destination indexes one record at a time, if 'Record batch' " +
          "the destination bulk indexes all the records in the batch. ",
      displayPosition = 40,
      displayMode = ConfigDef.DisplayMode.BASIC,
      group = "SOLR"
  )
  @ValueChooserModel(ProcessingModeChooserValues.class)
  public ProcessingMode indexingMode;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.BOOLEAN,
      defaultValue = "true",
      label = "Map Fields Automatically",
      description = "Maps record fields to Solr columns with matching names. Records must contain matching fields for" +
          " each required column in the schema",
      displayPosition = 50,
      displayMode = ConfigDef.DisplayMode.ADVANCED,
      group = "SOLR"
  )
  public boolean fieldsAlreadyMappedInRecord;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.MODEL,
      defaultValue="",
      label = "Fields",
      description = "Map record fields to Solr columns. Map fields to all required columns",
      displayPosition = 50,
      displayMode = ConfigDef.DisplayMode.ADVANCED,
      group = "SOLR",
      dependencies = {
          @Dependency(configName = "fieldsAlreadyMappedInRecord", triggeredByValues = "false")
      }
  )
  @ListBeanModel
  public List<SolrFieldMappingConfig> fieldNamesMap;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.STRING,
      defaultValue = "/",
      label = "Field Path for Data",
      displayPosition = 55,
      displayMode = ConfigDef.DisplayMode.ADVANCED,
      description = "Field path that contains the data to write to Solr",
      group = "SOLR",
      dependencies = {
          @Dependency(configName = "fieldsAlreadyMappedInRecord", triggeredByValues = "true")
      }
  )
  public String recordSolrFieldsPath;

  @ConfigDef(
      required = false,
      type = ConfigDef.Type.LIST,
      defaultValue = "[]",
      label = "Auto-generated Fields",
      description = "Fields that are generated by Solr if not provided in the record",
      displayPosition = 57,
      displayMode = ConfigDef.DisplayMode.ADVANCED,
      group = "SOLR"
  )
  public List<String> autogeneratedFields;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.BOOLEAN,
      defaultValue = "true",
      label = "Ignore Optional Fields",
      displayPosition = 59,
      displayMode = ConfigDef.DisplayMode.ADVANCED,
      group = "SOLR"
  )
  public boolean ignoreOptionalFields = false;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.MODEL,
      defaultValue= "TO_ERROR",
      label = "Missing Fields",
      description = "Action to take when fields are empty",
      displayPosition = 60,
      displayMode = ConfigDef.DisplayMode.ADVANCED,
      group = "SOLR"
  )
  @ValueChooserModel(MissingFieldActionChooserValues.class)
  public MissingFieldAction missingFieldAction = MissingFieldAction.TO_ERROR;


  @ConfigDef(
      required = true,
      type = ConfigDef.Type.BOOLEAN,
      defaultValue = "false",
      label = "Skip Validation",
      displayPosition = 80,
      displayMode = ConfigDef.DisplayMode.ADVANCED,
      group = "SOLR"
  )
  public boolean skipValidation;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.BOOLEAN,
      defaultValue = "true",
      label = "Wait Flush",
      description = "Block until index changes are flushed to disk",
      displayPosition = 1000,
      displayMode = ConfigDef.DisplayMode.ADVANCED,
      group = "SOLR"
  )
  public boolean waitFlush = true;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.BOOLEAN,
      defaultValue = "true",
      label = "Wait Searcher",
      description = "Block until a new searcher is opened and registered as the main query searcher, making the" +
          " changes visible",
      displayPosition = 1010,
      displayMode = ConfigDef.DisplayMode.ADVANCED,
      group = "SOLR"
  )
  public boolean waitSearcher = true;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.BOOLEAN,
      defaultValue = "false",
      label = "Soft Commit",
      description = "Makes index changes visible while neither fsync-ing index files nor writing a new index" +
          " descriptor",
      displayPosition = 1020,
      displayMode = ConfigDef.DisplayMode.ADVANCED,
      group = "SOLR"
  )
  public boolean softCommit = false;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.NUMBER,
      min = 0,
      defaultValue = "0",
      label = "Connection Timeout (ms)",
      description = "Connection timeout in milliseconds. 0 indicates no timeout",
      displayPosition = 1030,
      displayMode = ConfigDef.DisplayMode.ADVANCED,
      group = "SOLR"
  )
  public int connectionTimeout = 0;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.NUMBER,
      min = 0,
      defaultValue = "0",
      label = "Socket Timeout (ms)",
      description = "Socket timeout in milliseconds. 0 indicates no timeout",
      displayPosition = 1040,
      displayMode = ConfigDef.DisplayMode.ADVANCED,
      group = "SOLR"
  )
  public int socketTimeout = 0;

}
