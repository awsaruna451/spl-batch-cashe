package com.uob.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MultiJobObject implements Serializable {

    Map<String, List<Customer>> customerMap;
    Map<String, List<Manager>> managerMap;
}
