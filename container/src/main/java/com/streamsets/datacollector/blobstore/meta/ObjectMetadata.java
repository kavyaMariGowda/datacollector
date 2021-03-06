/*
 * Copyright 2018 StreamSets Inc.
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
package com.streamsets.datacollector.blobstore.meta;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ObjectMetadata {

  private final Map<Integer, String> contentUuid;

  public ObjectMetadata() {
    this.contentUuid = new HashMap<>();
  }

  @JsonCreator
  public ObjectMetadata(
    @JsonProperty("contentUuid") Map<Integer, String> contentUuid
  ) {
    this.contentUuid = contentUuid;
  }

  @JsonIgnore
  public boolean containsVersion(int version) {
    return contentUuid.containsKey(version);
  }

  @JsonIgnore
  public void createContent(int version, String uuid) {
    contentUuid.put(version, uuid);
  }

  @JsonIgnore
  public String uuidForVersion(int version) {
    return contentUuid.get(version);
  }

  @JsonIgnore
  public int latestVersion() {
    int version = Integer.MIN_VALUE;

    for(Integer v : contentUuid.keySet()) {
      if(v > version) {
        version = v;
      }
    }

    return version;
  }

  @JsonIgnore
  public void removeVersion(int version) {
    contentUuid.remove(version);
  }

  @JsonIgnore
  public Set<Integer> allVersions() {
    return new HashSet<>(contentUuid.keySet());
  }

  @JsonIgnore
  public boolean isEmpty() {
    return contentUuid.isEmpty();
  }
}
