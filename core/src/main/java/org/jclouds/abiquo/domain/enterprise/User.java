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

import static com.google.common.base.Preconditions.checkNotNull;

import org.jclouds.abiquo.AbiquoContext;
import org.jclouds.abiquo.domain.DomainWrapper;
import org.jclouds.abiquo.reference.ValidationErrors;

import com.abiquo.model.rest.RESTLink;
import com.abiquo.server.core.enterprise.RoleDto;
import com.abiquo.server.core.enterprise.UserDto;

/**
 * Adds high level functionality to {@link UserDto}.
 * 
 * @author Ignasi Barrera
 * @author Francesc Montserrat
 * @see http://community.abiquo.com/display/ABI20/Users+Resource
 */
public class User extends DomainWrapper<UserDto>
{
    /** Default active value of the user */
    private static final boolean DEFAULT_ACTIVE = true;

    /** The default authentication type. */
    private static final String DEFAULT_AUTH_TYPE = "ABIQUO";

    /** The default locale for the user. */
    private static final String DEFAULT_LOCALE = "en_US";

    /** The enterprise where the user belongs. */
    // Package protected to allow navigation from children
    Enterprise enterprise;

    /** Role of the user. */
    // Package protected to allow navigation from children
    Role role;

    /**
     * Constructor to be used only by the builder.
     */
    protected User(final AbiquoContext context, final UserDto target)
    {
        super(context, target);
    }

    // Domain operations

    public void delete()
    {
        context.getApi().getEnterpriseClient().deleteUser(target);
        target = null;
    }

    public void save()
    {
        target.addLink(new RESTLink("role", role.unwrap().getEditLink().getHref()));
        target = context.getApi().getEnterpriseClient().createUser(enterprise.unwrap(), target);
    }

    public void update()
    {
        target = context.getApi().getEnterpriseClient().updateUser(target);
    }

    // Children access

    public Role getRole()
    {
        RoleDto role = context.getApi().getAdminClient().getRole(target);
        return wrap(context, Role.class, role);
    }

    // Builder

    public static Builder builder(final AbiquoContext context, final Enterprise enterprise,
        final Role role)
    {
        return new Builder(context, enterprise, role);
    }

    public static class Builder
    {
        private AbiquoContext context;

        private Enterprise enterprise;

        private Role role;

        private String name;

        private String nick;

        private String locale = DEFAULT_LOCALE;

        private String password;

        private String surname;

        private boolean active = DEFAULT_ACTIVE;

        private String email;

        private String description;

        private String authType = DEFAULT_AUTH_TYPE;

        public Builder(final AbiquoContext context, final Enterprise enterprise, final Role role)
        {
            super();
            checkNotNull(enterprise, ValidationErrors.NULL_PARENT + Enterprise.class);
            checkNotNull(role, ValidationErrors.NULL_PARENT + Role.class);
            this.context = context;
            this.enterprise = enterprise;
            this.role = role;
        }

        public Builder enterprise(final Enterprise enterprise)
        {
            checkNotNull(enterprise, ValidationErrors.NULL_PARENT + Enterprise.class);
            this.enterprise = enterprise;
            return this;
        }

        public Builder role(final Role role)
        {
            this.role = role;
            return this;
        }

        public Builder name(final String name, final String surname)
        {
            this.name = name;
            this.surname = surname;
            return this;
        }

        public Builder nick(final String nick)
        {
            this.nick = nick;
            return this;
        }

        public Builder locale(final String locale)
        {
            this.locale = locale;
            return this;
        }

        public Builder password(final String password)
        {
            this.password = password;
            return this;
        }

        public Builder active(final boolean active)
        {
            this.active = active;
            return this;
        }

        public Builder email(final String email)
        {
            this.email = email;
            return this;
        }

        public Builder description(final String description)
        {
            this.description = description;
            return this;
        }

        public Builder authType(final String authType)
        {
            this.authType = authType;
            return this;
        }

        public User build()
        {
            UserDto dto = new UserDto();
            dto.setActive(active);
            dto.setAuthType(authType);
            dto.setDescription(description);
            dto.setEmail(email);
            dto.setLocale(locale);
            dto.setName(name);
            dto.setNick(nick);
            dto.setPassword(password);
            dto.setSurname(surname);
            User user = new User(context, dto);
            user.enterprise = enterprise;
            user.role = role;

            return user;
        }

        public static Builder fromUser(final User in)
        {
            return User.builder(in.context, in.enterprise, in.role).active(in.isActive()).authType(
                in.getAuthType()).description(in.getDescription()).email(in.getEmail()).locale(
                in.getLocale()).name(in.getName(), in.getSurname()).nick(in.getNick()).password(
                in.getPassword());
        }
    }

    // Delegate methods

    public String getAuthType()
    {
        return target.getAuthType();
    }

    public String getDescription()
    {
        return target.getDescription();
    }

    public String getEmail()
    {
        return target.getEmail();
    }

    public Integer getId()
    {
        return target.getId();
    }

    public String getLocale()
    {
        return target.getLocale();
    }

    public String getName()
    {
        return target.getName();
    }

    public String getNick()
    {
        return target.getNick();
    }

    public String getPassword()
    {
        return target.getPassword();
    }

    public String getSurname()
    {
        return target.getSurname();
    }

    public boolean isActive()
    {
        return target.isActive();
    }

    public void setActive(final boolean active)
    {
        target.setActive(active);
    }

    public void setAuthType(final String authType)
    {
        target.setAuthType(authType);
    }

    public void setDescription(final String description)
    {
        target.setDescription(description);
    }

    public void setEmail(final String email)
    {
        target.setEmail(email);
    }

    public void setLocale(final String locale)
    {
        target.setLocale(locale);
    }

    public void setName(final String name)
    {
        target.setName(name);
    }

    public void setNick(final String nick)
    {
        target.setNick(nick);
    }

    public void setPassword(final String password)
    {
        target.setPassword(password);
    }

    public void setSurname(final String surname)
    {
        target.setSurname(surname);
    }
}
