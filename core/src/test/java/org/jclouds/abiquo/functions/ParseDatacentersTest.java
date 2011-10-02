/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jclouds.abiquo.functions;

import static org.jclouds.abiquo.functions.ParseDatacenterTest.datacenterPayload;
import static org.jclouds.abiquo.functions.ParseDatacenterTest.verifyDatacenter;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.jclouds.abiquo.xml.internal.JAXBParser;
import org.testng.annotations.Test;

import com.abiquo.server.core.infrastructure.DatacenterDto;
import com.abiquo.server.core.infrastructure.DatacentersDto;
import com.google.inject.TypeLiteral;

/**
 * Test Datacenters parsing.
 * 
 * @author Ignasi Barrera
 */
@Test(groups = "unit")
public class ParseDatacentersTest extends ParseXMLTest<DatacentersDto>
{

    @Override
    protected ParseDatacenters getParser()
    {
        return new ParseDatacenters(new JAXBParser(), TypeLiteral.get(DatacentersDto.class));
    }

    @Override
    protected String getPayload()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        builder.append("<datacenters>");
        builder.append(datacenterPayload());
        builder.append("</datacenters>");
        return builder.toString();
    }

    @Override
    protected void verifyObject(DatacentersDto object)
    {
        assertNotNull(object);
        assertNotNull(object.getCollection());
        assertEquals(object.getCollection().size(), 1);

        DatacenterDto datacenter = object.getCollection().get(0);
        verifyDatacenter(datacenter);
    }

}