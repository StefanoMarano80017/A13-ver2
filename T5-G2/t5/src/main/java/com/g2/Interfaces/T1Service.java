/*
 *   Copyright (c) 2024 Stefano Marano https://github.com/StefanoMarano80017
 *   All rights reserved.

 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at

 *   http://www.apache.org/licenses/LICENSE-2.0

 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.g2.Interfaces;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.g2.Model.ClassUT;

@Service
public class T1Service extends BaseService {

    private static final String BASE_URL = "http://manvsclass-controller-1:8080";

    public T1Service(RestTemplate restTemplate) {
        super(restTemplate, BASE_URL);
        // Registrazione delle azioni
        registerAction("getClasses", new ServiceActionDefinition(
                params -> getClasses() // Metodo senza argomenti
        ));
        registerAction("getClassUnderTest", new ServiceActionDefinition(
                params -> getClassUnderTest((String) params[0]),
                String.class));
    }

    // Metodi effettivi
    private List<ClassUT> getClasses(){
        return callRestGET("/home", null, new ParameterizedTypeReference<List<ClassUT>>() {});
    }

    private String getClassUnderTest(String nomeCUT) {
        byte[] result = callRestGET("/downloadFile/" + nomeCUT, null, byte[].class);
        return removeBOM(convertToString(result));
    }

}
