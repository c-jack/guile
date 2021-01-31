/*
 * Copyright 2022 Chris Jackson [github.com/c-jack]
 */

package uk.cjack.guile.model;

import java.util.ArrayList;
import java.util.List;

import liquibase.change.CheckSum;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;

public class ChangesetItem
{
    private final String id;
    private final CheckSum storedCheckSum;
    private final String author;
    private final String filePath;
    private String parentPath;
    private final int changeSetSize;
    private final boolean alwaysRun;
    private final boolean runOnChange;
    private final List<ChangesetItem> children = new ArrayList<>();

    public ChangesetItem( final ChangeSet changeSet )
    {
        this.id = changeSet.getId();
        this.storedCheckSum = changeSet.getStoredCheckSum();
        this.author = changeSet.getAuthor();
        this.filePath = changeSet.getChangeLog().getPhysicalFilePath();
        this.changeSetSize = changeSet.getChanges().size();
        this.alwaysRun = changeSet.isAlwaysRun();
        this.runOnChange = changeSet.isRunOnChange();
        final DatabaseChangeLog parentChangeLog = changeSet.getChangeLog().getParentChangeLog();
        if ( parentChangeLog != null )
        {
            this.parentPath = parentChangeLog.getPhysicalFilePath();
        }
    }

    public String getId()
    {
        return id;
    }

    public CheckSum getStoredCheckSum()
    {
        return storedCheckSum;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public String getParentPath()
    {
        return parentPath;
    }

    public int getChangeSetSize()
    {
        return changeSetSize;
    }

    public boolean isAlwaysRun()
    {
        return alwaysRun;
    }

    public boolean isRunOnChange()
    {
        return runOnChange;
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
