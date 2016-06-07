/*
 * Copyright (c) 2016 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.stmt.semver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.opendaylight.yangtools.yang.model.repo.api.StatementParserMode;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import org.junit.Test;
import org.opendaylight.yangtools.concepts.SemVer;
import org.opendaylight.yangtools.yang.model.api.Module;
import org.opendaylight.yangtools.yang.model.api.SchemaContext;
import org.opendaylight.yangtools.yang.model.util.RevisionAwareXPathImpl;
import org.opendaylight.yangtools.yang.model.util.SchemaContextUtil;
import org.opendaylight.yangtools.yang.parser.spi.meta.ReactorException;
import org.opendaylight.yangtools.yang.parser.spi.source.SourceException;
import org.opendaylight.yangtools.yang.stmt.StmtTestUtils;

public class SemanticVersionComplexTest {

    @Test
    public void complexTest1() throws SourceException, FileNotFoundException, ReactorException, URISyntaxException,
            ParseException {
        SchemaContext context = StmtTestUtils.parseYangSources("/semantic-version/complex/complex-1",
                StatementParserMode.SEMVER_MODE);
        assertNotNull(context);

        Module foo = context.findModuleByNamespace(new URI("foo")).iterator().next();
        Module semVer = context.findModuleByNamespace(new URI("urn:opendaylight:yang:extension:semantic-version"))
                .iterator().next();

        // check module versions
        assertEquals(SemVer.valueOf("1.3.95"), semVer.getSemanticVersion());
        assertEquals(SemVer.valueOf("1.50.2"), foo.getSemanticVersion());

        Module bar = StmtTestUtils.findImportedModule(context, foo, "bar");
        assertEquals(SemVer.valueOf("1.2.6"), bar.getSemanticVersion());

        Module foobar = StmtTestUtils.findImportedModule(context, bar, "foobar");
        assertEquals(SemVer.valueOf("2.26.465"), foobar.getSemanticVersion());

        // check imported components
        assertNotNull("This component should be present", SchemaContextUtil.findDataSchemaNode(context, foo,
                new RevisionAwareXPathImpl("/bar:root/bar:test-container/bar:number", true)));

        assertNotNull("This component should be present", SchemaContextUtil.findDataSchemaNode(context, foo,
                new RevisionAwareXPathImpl("/bar:should-present", true)));

        // check not imported components
        assertNull("This component should not be present", SchemaContextUtil.findDataSchemaNode(context, foo,
                new RevisionAwareXPathImpl("/bar:root/bar:test-container/bar:oldnumber", true)));

        assertNull("This component should not be present", SchemaContextUtil.findDataSchemaNode(context, foo,
                new RevisionAwareXPathImpl("/bar:should-not-be-present", true)));
    }

    @Test
    public void complexTest2() throws SourceException, FileNotFoundException, ReactorException, URISyntaxException,
            ParseException {
        SchemaContext context = StmtTestUtils.parseYangSources("/semantic-version/complex/complex-2",
                StatementParserMode.SEMVER_MODE);
        assertNotNull(context);

        Module foo = context.findModuleByNamespace(new URI("foo")).iterator().next();
        Module semVer = context.findModuleByNamespace(new URI("urn:opendaylight:yang:extension:semantic-version"))
                .iterator().next();

        // check module versions
        assertEquals(SemVer.valueOf("2.5.50"), semVer.getSemanticVersion());
        assertEquals(SemVer.valueOf("2.32.2"), foo.getSemanticVersion());

        Module bar = StmtTestUtils.findImportedModule(context, foo, "bar");
        assertEquals(SemVer.valueOf("4.9.8"), bar.getSemanticVersion());

        Module foobar = StmtTestUtils.findImportedModule(context, bar, "foobar");
        assertEquals(SemVer.valueOf("7.13.99"), foobar.getSemanticVersion());

        // check used augmentations
        assertNotNull("This component should be present", SchemaContextUtil.findDataSchemaNode(context, bar,
                new RevisionAwareXPathImpl("/foobar:root/foobar:test-container/bar:should-present-leaf-1", true)));

        assertNotNull("This component should be present", SchemaContextUtil.findDataSchemaNode(context, bar,
                new RevisionAwareXPathImpl("/foobar:root/foobar:test-container/bar:should-present-leaf-2", true)));

        // check not used augmentations
        assertNull("This component should not be present",
                SchemaContextUtil.findDataSchemaNode(context, bar, new RevisionAwareXPathImpl(
                        "/foobar:root/foobar:test-container/bar:should-not-be-present-leaf-1", true)));

        assertNull("This component should not be present",
                SchemaContextUtil.findDataSchemaNode(context, bar, new RevisionAwareXPathImpl(
                        "/foobar:root/foobar:test-container/bar:should-not-be-present-leaf-2", true)));

        // check if correct foobar module was included
        assertNotNull("This component should be present", SchemaContextUtil.findDataSchemaNode(context, bar,
                new RevisionAwareXPathImpl("/foobar:root/foobar:included-correct-mark", true)));

        assertNull("This component should not be present", SchemaContextUtil.findDataSchemaNode(context, bar,
                new RevisionAwareXPathImpl("/foobar:root/foobar:included-not-correct-mark", true)));
    }
}