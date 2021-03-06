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

package org.jclouds.abiquo.domain;

import com.abiquo.model.enumerator.RemoteServiceType;
import com.abiquo.model.rest.RESTLink;
import com.abiquo.server.core.infrastructure.DatacenterDto;
import com.abiquo.server.core.infrastructure.MachineDto;
import com.abiquo.server.core.infrastructure.RackDto;
import com.abiquo.server.core.infrastructure.RemoteServiceDto;

/**
 * Infrastructure domain utilities.
 * 
 * @author Ignasi Barrera
 */
public class InfrastructureResources
{
    public static DatacenterDto datacenterPost()
    {
        DatacenterDto datacenter = new DatacenterDto();
        datacenter.setName("DC");
        datacenter.setLocation("Honolulu");
        return datacenter;
    }

    public static RackDto rackPost()
    {
        RackDto rack = new RackDto();
        rack.setName("Aloha");
        rack.setShortDescription("A hawaian rack");
        rack.setHaEnabled(false);
        rack.setVlanIdMin(6);
        rack.setVlanIdMax(3024);
        rack.setVlanPerVdcReserved(6);
        rack.setNrsq(80);
        return rack;
    }

    public static MachineDto machinePost()
    {
        MachineDto machine = new MachineDto();
        machine.setName("Kamehameha");
        machine.setVirtualCpuCores(3);
        machine.setDescription("A hawaian machine");
        machine.setVirtualRamInMb(512);
        machine.setVirtualSwitch("192.168.1.10");
        return machine;
    }

    public static RemoteServiceDto remoteServicePost()
    {
        RemoteServiceDto remoteService = new RemoteServiceDto();
        remoteService.setType(RemoteServiceType.NODE_COLLECTOR);
        remoteService.setUri("http://localhost:80/nodecollector");
        remoteService.setStatus(0);
        return remoteService;
    }

    public static DatacenterDto datacenterPut()
    {
        DatacenterDto datacenter = datacenterPost();
        datacenter.setId(1);
        datacenter.addLink(new RESTLink("discovermultiple",
            "http://localhost/api/admin/datacenters/1/action/discovermultiple"));
        datacenter.addLink(new RESTLink("discoversingle",
            "http://localhost/api/admin/datacenters/1/action/discoversingle"));
        datacenter.addLink(new RESTLink("edit", "http://localhost/api/admin/datacenters/1"));
        datacenter.addLink(new RESTLink("getLimits",
            "http://localhost/api/admin/datacenters/1/action/getLimits"));
        datacenter.addLink(new RESTLink("racks", "http://localhost/api/admin/datacenters/1/racks"));
        datacenter.addLink(new RESTLink("remoteservices",
            "http://localhost/api/admin/datacenters/1/remoteservices"));
        return datacenter;
    }

    public static RackDto rackPut()
    {
        RackDto rack = rackPost();
        rack.setId(1);
        rack.addLink(new RESTLink("datacenter", "http://localhost/api/admin/datacenters/1"));
        rack.addLink(new RESTLink("edit", "http://localhost/api/admin/datacenters/1/racks/1"));
        rack.addLink(new RESTLink("machines",
            "http://localhost/api/admin/datacenters/1/racks/1/machines"));
        return rack;
    }

    public static RemoteServiceDto remoteServicePut()
    {
        RemoteServiceDto remoteService = remoteServicePost();
        remoteService.setId(1);
        remoteService.addLink(new RESTLink("check",
            "http://localhost/api/admin/datacenters/1/remoteservices/nodecollector/action/check"));
        remoteService
            .addLink(new RESTLink("datacenter", "http://localhost/api/admin/datacenters/1"));
        remoteService.addLink(new RESTLink("edit",
            "http://localhost/api/admin/datacenters/1/remoteservices/nodecollector"));
        return remoteService;
    }

    public static MachineDto machinePut()
    {
        MachineDto machine = machinePost();
        machine.setId(1);
        machine.addLink(new RESTLink("edit",
            "http://localhost/api/admin/datacenters/1/racks/1/machines/1"));
        machine.addLink(new RESTLink("rack", "http://localhost/api/admin/datacenters/1/racks/1"));
        machine.setVirtualCpuCores(5);

        return machine;
    }

    public static String datacenterPostPayload()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<datacenter>");
        buffer.append("<location>Honolulu</location>");
        buffer.append("<name>DC</name>");
        buffer.append("</datacenter>");
        return buffer.toString();
    }

    public static String rackPostPayload()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<rack>");
        buffer.append("<haEnabled>false</haEnabled>");
        buffer.append("<name>Aloha</name>");
        buffer.append("<nrsq>80</nrsq>");
        buffer.append("<shortDescription>A hawaian rack</shortDescription>");
        buffer.append("<vlanIdMax>3024</vlanIdMax>");
        buffer.append("<vlanIdMin>6</vlanIdMin>");
        buffer.append("<vlanPerVdcReserved>6</vlanPerVdcReserved>");
        buffer.append("</rack>");
        return buffer.toString();
    }

    public static String machinePostPayload()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<machine>");
        buffer.append("<datastores/>");
        buffer.append("<description>A hawaian machine</description>");
        buffer.append("<name>Kamehameha</name>");
        buffer.append("<cpu>3</cpu>");
        buffer.append("<cpuRatio>1</cpuRatio>");
        buffer.append("<cpuUsed>1</cpuUsed>");
        buffer.append("<ram>512</ram>");
        buffer.append("<ramUsed>1</ramUsed>");
        buffer.append("<virtualSwitch>192.168.1.10</virtualSwitch>");
        buffer.append("</machine>");
        return buffer.toString();
    }

    public static String remoteServicePostPayload()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<remoteService>");
        buffer.append("<status>0</status>");
        buffer.append("<type>NODE_COLLECTOR</type>");
        buffer.append("<uri>http://localhost:80/nodecollector</uri>");
        buffer.append("</remoteService>");
        return buffer.toString();
    }

    public static String datacenterPutPayload()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<datacenter>");
        buffer.append(link("/admin/datacenters/1/action/discovermultiple", "discovermultiple"));
        buffer.append(link("/admin/datacenters/1/action/discoversingle", "discoversingle"));
        buffer.append(link("/admin/datacenters/1", "edit"));
        buffer.append(link("/admin/datacenters/1/action/getLimits", "getLimits"));
        buffer.append(link("/admin/datacenters/1/racks", "racks"));
        buffer.append(link("/admin/datacenters/1/remoteservices", "remoteservices"));
        buffer.append("<id>1</id>");
        buffer.append("<location>Honolulu</location>");
        buffer.append("<name>DC</name>");
        buffer.append("</datacenter>");
        return buffer.toString();
    }

    public static String rackPutPayload()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<rack>");
        buffer.append(link("/admin/datacenters/1", "datacenter"));
        buffer.append(link("/admin/datacenters/1/racks/1", "edit"));
        buffer.append(link("/admin/datacenters/1/racks/1/machines", "machines"));
        buffer.append("<haEnabled>false</haEnabled>");
        buffer.append("<id>1</id>");
        buffer.append("<name>Aloha</name>");
        buffer.append("<nrsq>80</nrsq>");
        buffer.append("<shortDescription>A hawaian rack</shortDescription>");
        buffer.append("<vlanIdMax>3024</vlanIdMax>");
        buffer.append("<vlanIdMin>6</vlanIdMin>");
        buffer.append("<vlanPerVdcReserved>6</vlanPerVdcReserved>");
        buffer.append("</rack>");
        return buffer.toString();
    }

    public static String remoteServicePutPayload()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<remoteService>");
        buffer.append(link("/admin/datacenters/1/remoteservices/nodecollector/action/check",
            "check"));
        buffer.append(link("/admin/datacenters/1", "datacenter"));
        buffer.append(link("/admin/datacenters/1/remoteservices/nodecollector", "edit"));
        buffer.append("<id>1</id>");
        buffer.append("<status>0</status>");
        buffer.append("<type>NODE_COLLECTOR</type>");
        buffer.append("<uri>http://localhost:80/nodecollector</uri>");
        buffer.append("</remoteService>");
        return buffer.toString();
    }

    public static String machinePutPayload()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<machine>");
        buffer.append(link("/admin/datacenters/1/racks/1/machines/1", "edit"));
        buffer.append(link("/admin/datacenters/1/racks/1", "rack"));
        buffer.append("<datastores/>");
        buffer.append("<description>A hawaian machine</description>");
        buffer.append("<id>1</id>");
        buffer.append("<name>Kamehameha</name>");
        buffer.append("<cpu>5</cpu>");
        buffer.append("<cpuRatio>1</cpuRatio>");
        buffer.append("<cpuUsed>1</cpuUsed>");
        buffer.append("<ram>512</ram>");
        buffer.append("<ramUsed>1</ramUsed>");
        buffer.append("<virtualSwitch>192.168.1.10</virtualSwitch>");
        buffer.append("</machine>");
        return buffer.toString();
    }

    private static String link(final String href, final String rel)
    {
        return "<link href=\"http://localhost/api" + href + "\" rel=\"" + rel + "\"/>";
    }
}
