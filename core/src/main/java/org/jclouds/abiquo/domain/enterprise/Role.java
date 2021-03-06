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

package org.jclouds.abiquo.domain.enterprise;

import org.jclouds.abiquo.AbiquoContext;
import org.jclouds.abiquo.domain.DomainWrapper;

import com.abiquo.server.core.enterprise.RoleDto;

/**
 * Adds high level functionality to {@link RoleDto}.
 * 
 * @author Ignasi Barrera
 * @author Francesc Montserrat
 * @see http://community.abiquo.com/display/ABI20/Roles+Resource
 */
public class Role extends DomainWrapper<RoleDto>
{
    /** Default active value of the user */
    private static final boolean DEFAULT_BLOCKED = false;

    /**
     * Constructor to be used only by the builder.
     */
    protected Role(final AbiquoContext context, final RoleDto target)
    {
        super(context, target);
    }

    // Domain operations

    public void delete()
    {
        context.getApi().getAdminClient().deleteRole(target);
        target = null;
    }

    public void save()
    {
        target = context.getApi().getAdminClient().createRole(target);
    }

    public void update()
    {
        target = context.getApi().getAdminClient().updateRole(target);
    }

    // Builder

    public static Builder builder(final AbiquoContext context)
    {
        return new Builder(context);
    }

    public static class Builder
    {
        private AbiquoContext context;

        private String name;

        private boolean blocked = DEFAULT_BLOCKED;

        public Builder(final AbiquoContext context)
        {
            super();
            this.context = context;
        }

        public Builder name(final String name)
        {
            this.name = name;
            return this;
        }

        public Builder blocked(final boolean blocked)
        {
            this.blocked = blocked;
            return this;
        }

        public Role build()
        {
            RoleDto dto = new RoleDto();
            dto.setName(name);
            dto.setBlocked(blocked);
            Role role = new Role(context, dto);

            return role;
        }

        public static Builder fromRole(final Role in)
        {
            return Role.builder(in.context).blocked(in.isBlocked()).name(in.getName());
        }
    }

    // Delegate methods

    public Integer getId()
    {
        return target.getId();
    }

    public String getName()
    {
        return target.getName();
    }

    public boolean isBlocked()
    {
        return target.isBlocked();
    }

    public void setBlocked(final boolean blocked)
    {
        target.setBlocked(blocked);
    }

    public void setName(final String name)
    {
        target.setName(name);
    }
}
