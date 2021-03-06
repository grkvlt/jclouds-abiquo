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

package org.jclouds.abiquo.domain.infrastructure;

import static org.jclouds.abiquo.util.Assert.assertHasError;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import javax.ws.rs.core.Response.Status;

import org.jclouds.abiquo.AbiquoContext;
import org.jclouds.abiquo.domain.exception.AbiquoException;
import org.jclouds.abiquo.domain.infrastructure.Machine.Builder;
import org.jclouds.abiquo.environment.InfrastructureTestEnvironment;
import org.jclouds.abiquo.features.BaseAbiquoClientLiveTest;
import org.jclouds.abiquo.util.Config;
import org.testng.annotations.Test;

import com.abiquo.model.enumerator.HypervisorType;
import com.abiquo.model.enumerator.RemoteServiceType;
import com.abiquo.server.core.infrastructure.MachineDto;

/**
 * Live integration tests for the {@link Machine} domain class.
 * 
 * @author Ignasi Barrera
 */
@Test(groups = "live")
public class MachineLiveTest extends BaseAbiquoClientLiveTest<InfrastructureTestEnvironment>
{
    @Override
    protected InfrastructureTestEnvironment environment(final AbiquoContext context)
    {
        return new InfrastructureTestEnvironment(context);
    }

    public void testDiscoverMachineWithouRemoteService()
    {
        RemoteService nc = env.findRemoteService(RemoteServiceType.NODE_COLLECTOR);
        nc.delete();

        try
        {
            String ip = Config.get("abiquo.hypervisor.address");
            HypervisorType type = HypervisorType.valueOf(Config.get("abiquo.hypervisor.type"));
            String user = Config.get("abiquo.hypervisor.user");
            String pass = Config.get("abiquo.hypervisor.pass");

            env.datacenter.discoverSingleMachine(ip, type, user, pass);
        }
        catch (AbiquoException ex)
        {
            assertHasError(ex, Status.NOT_FOUND, "RS-2");
        }
    }

    public void testCreate()
    {
        Machine newmachine =
            Builder.fromMachine(env.machine).hypervisorType(HypervisorType.XEN_3).ip("10.60.1.98")
                .ipService("10.60.1.98").build();

        newmachine.save();
        assertNotNull(newmachine.getId());

        newmachine.delete();
    }

    public void testUpdate()
    {
        env.machine.setName("API Machine");
        env.machine.update();

        // Recover the updated machine
        MachineDto updated = env.infrastructureClient.getMachine(env.rack.unwrap(), env.machine.getId());
        assertEquals(updated.getName(), "API Machine");
    }

    public void testFindDatastore()
    {
        Datastore datastore = env.machine.getDatastores().get(0);
        Datastore found = env.machine.findDatastore(datastore.getName());
        assertEquals(found.getName(), datastore.getName());
    }

    public void testFindAvailableVirtualSwitch()
    {
        String vswitch = env.machine.getAvailableVirtualSwitches().get(0);
        String found = env.machine.findAvailableVirtualSwitch(vswitch);
        assertEquals(found, vswitch);
    }
}
