/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.model.api.stmt;

import java.util.Collection;
import javax.annotation.Nonnull;

@Rfc6020AbnfRule("linkage-stms")
public interface LinkageGroup {

    @Nonnull Collection<? extends ImportStatement> getImports();

    @Nonnull Collection<? extends IncludeStatement> getIncludes();
}
