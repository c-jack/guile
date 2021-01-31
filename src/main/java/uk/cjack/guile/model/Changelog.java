/*
 * Copyright 2022 Chris Jackson [github.com/c-jack]
 */

package uk.cjack.guile.model;

import java.util.ArrayList;
import java.util.List;

import liquibase.change.CheckSum;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;

public class Changelog
{
    private final String path;
    private final List<ChangesetItem> children = new ArrayList<>();

    public Changelog( final String filePath )
    {
        this.path = filePath;
    }

    public String getPath()
    {
        return path;
    }

    public List<ChangesetItem> getChildren()
    {
        return children;
    }

    public void addChild( final ChangesetItem child )
    {
        this.children.add( child );
    }
}
