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

package org.jclouds.abiquo.predicates.infrastructure;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Arrays;

import org.jclouds.abiquo.domain.infrastructure.Datastore;

import com.abiquo.server.core.infrastructure.DatastoreDto;
import com.google.common.base.Predicate;

/**
 * Container for {@link DatastoreDto} filters.
 * 
 * @author Ignasi Barrera
 */
public class DatastorePredicates
{
    public static Predicate<Datastore> datastoreName(final String name)
    {
        return datastoreNames(checkNotNull(name, "name must be defined"));
    }

    public static Predicate<Datastore> datastoreNames(final String... names)
    {
        checkNotNull(names, "names must be defined");

        return new Predicate<Datastore>()
        {
            @Override
            public boolean apply(final Datastore datastore)
            {
                return Arrays.asList(names).contains(datastore.getName());
            }
        };
    }
}