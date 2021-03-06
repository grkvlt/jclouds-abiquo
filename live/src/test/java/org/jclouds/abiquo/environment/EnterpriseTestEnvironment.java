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

package org.jclouds.abiquo.environment;

import static org.jclouds.abiquo.reference.AbiquoTestConstants.PREFIX;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.util.UUID;

import org.jclouds.abiquo.AbiquoContext;
import org.jclouds.abiquo.domain.enterprise.Enterprise;
import org.jclouds.abiquo.domain.enterprise.Role;
import org.jclouds.abiquo.domain.enterprise.User;
import org.jclouds.abiquo.domain.infrastructure.Datacenter;
import org.jclouds.abiquo.features.AdminClient;
import org.jclouds.abiquo.features.AdministrationService;
import org.jclouds.abiquo.features.EnterpriseClient;
import org.jclouds.abiquo.features.InfrastructureClient;
import org.jclouds.abiquo.predicates.enterprise.UserPredicates;

/**
 * Test environment for enterprise live tests.
 * 
 * @author Ignasi Barrera
 */
public class EnterpriseTestEnvironment implements TestEnvironment
{
    /** The rest context. */
    private AbiquoContext context;

    // Environment data made public so tests can use them easily
    public EnterpriseClient enterpriseClient;

    public InfrastructureClient infrastructureClient;

    public AdminClient adminClient;

    public Enterprise enterprise;

    public Datacenter datacenter;

    public User user;

    public Role role;

    public AdministrationService administrationService;

    public EnterpriseTestEnvironment(final AbiquoContext context)
    {
        super();
        this.administrationService = context.getAdministrationService();
        this.context = context;
        this.enterpriseClient = context.getApi().getEnterpriseClient();
        this.infrastructureClient = context.getApi().getInfrastructureClient();
        this.adminClient = context.getApi().getAdminClient();
    }

    @Override
    public void setup() throws Exception
    {
        createEnterprise();
        createRole();
        createUser();
        createDatacenter();
    }

    @Override
    public void tearDown() throws Exception
    {
        deleteUser();
        deleteRole();
        deleteEnterprise();
        deleteDatacenter();
    }

    // Setup
    private void createUser()
    {
        Role role = administrationService.listRoles().iterator().next();
        user =
            User.builder(context, enterprise, role).name(randomName(), randomName()).nick(
                randomName()).authType("ABIQUO").description(randomName()).email(
                randomName() + "@abiquo.com").locale("en_US").password(
                "c69a39bd64ffb77ea7ee3369dce742f3").build();

        user.save();
        assertNotNull(user.getId());
        assertEquals(role.getId(), user.getRole().getId());
    }

    private void createRole()
    {
        role = Role.builder(context).name(randomName()).blocked(false).build();
        role.save();
        assertNotNull(role.getId());
    }

    private void createEnterprise()
    {
        enterprise = Enterprise.builder(context).name(randomName()).build();
        enterprise.save();
        assertNotNull(enterprise.getId());
    }

    private void createDatacenter()
    {
        datacenter = Datacenter.builder(context).name(randomName()).location("Honolulu").build();
        datacenter.save();
        assertNotNull(datacenter.getId());
    }

    // Tear down

    private void deleteEnterprise()
    {
        if (enterprise != null)
        {
            Integer idEnterprise = enterprise.getId();
            enterprise.delete();
            assertNull(enterpriseClient.getEnterprise(idEnterprise));
        }
    }

    private void deleteDatacenter()
    {
        if (datacenter != null)
        {
            Integer idDatacenter = datacenter.getId();
            datacenter.delete(); // Abiquo API will delete remote services too
            assertNull(infrastructureClient.getDatacenter(idDatacenter));
        }
    }

    private void deleteUser()
    {
        if (user != null)
        {
            String nick = user.getNick();
            user.delete();
            // Nick is unique in an enterprise
            assertNull(enterprise.findUser(UserPredicates.userNick(nick)));
        }
    }

    private void deleteRole()
    {
        if (role != null)
        {
            Integer roleId = role.getId();
            role.delete();
            assertNull(adminClient.getRole(roleId));
        }
    }

    // Utility methods

    private static String randomName()
    {
        return PREFIX + UUID.randomUUID().toString().substring(0, 12);
    }

}
