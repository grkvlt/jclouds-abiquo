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
import org.jclouds.abiquo.domain.DomainWithLimitsWrapper;
import org.jclouds.abiquo.domain.builder.LimitsBuilder;

import com.abiquo.server.core.enterprise.DatacenterLimitsDto;

/**
 * Adds high level functionality to {@link DatacenterLimitsDto}.
 * 
 * @author Ignasi Barrera
 * @author Francesc Montserrat
 * @see http://community.abiquo.com/display/ABI20/Datacenter+Limits+Resource
 */
public class Limits extends DomainWithLimitsWrapper<DatacenterLimitsDto>
{
    /**
     * Constructor to be used only by the builder.
     */
    protected Limits(final AbiquoContext context, final DatacenterLimitsDto target)
    {
        super(context, target);
    }

    // Domain operations

    public void update()
    {
        target = context.getApi().getEnterpriseClient().updateLimits(target);
    }

    // Builder

    public static Builder builder(final AbiquoContext context)
    {
        return new Builder(context);
    }

    public static class Builder extends LimitsBuilder<Builder>
    {
        private AbiquoContext context;

        public Builder(final AbiquoContext context)
        {
            super();
            this.context = context;
        }

        public Limits build()
        {
            DatacenterLimitsDto dto = new DatacenterLimitsDto();
            dto.setRamLimitsInMb(ramSoftLimitInMb, ramHardLimitInMb);
            dto.setCpuCountLimits(cpuCountSoftLimit, cpuCountHardLimit);
            dto.setHdLimitsInMb(hdSoftLimitInMb, hdHardLimitInMb);
            dto.setStorageLimits(storageSoft, storageHard);
            dto.setVlansLimits(vlansSoft, vlansHard);
            dto.setPublicIPLimits(publicIpsSoft, publicIpsHard);
            dto.setRepositoryHardLimitsInMb(repositoryHard);
            dto.setRepositorySoftLimitsInMb(repositorySoft);

            Limits limits = new Limits(context, dto);

            return limits;
        }

        public static Builder fromEnterprise(final Limits in)
        {
            return Limits.builder(in.context)
                .ramLimits(in.getRamSoftLimitInMb(), in.getRamHardLimitInMb())
                .cpuCountLimits(in.getCpuCountSoftLimit(), in.getCpuCountHardLimit())
                .hdLimitsInMb(in.getHdSoftLimitInMb(), in.getHdHardLimitInMb())
                .storageLimits(in.getStorageSoft(), in.getStorageHard())
                .vlansLimits(in.getVlansSoft(), in.getVlansHard())
                .publicIpsLimits(in.getPublicIpsSoft(), in.getPublicIpsHard())
                .repositoryLimits(in.getRepositorySoft(), in.getRepositoryHard());
        }
    }

    // Delegate methods

    public Integer getId()
    {
        return target.getId();
    }

    public long getRepositoryHard()
    {
        return target.getRepositoryHardLimitsInMb();
    }

    public long getRepositorySoft()
    {
        return target.getRepositorySoftLimitsInMb();
    }

    public void setRepositoryHard(final long repositoryHard)
    {
        target.setRepositoryHardLimitsInMb(repositoryHard);
    }

    public void setRepositoryLimits(final long soft, final long hard)
    {
        target.setRepositoryHardLimitsInMb(hard);
        target.setRepositorySoftLimitsInMb(soft);
    }

    public void setRepositorySoft(final long repositorySoft)
    {
        target.setRepositorySoftLimitsInMb(repositorySoft);
    }

}
