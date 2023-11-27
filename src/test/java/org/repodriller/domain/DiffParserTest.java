package org.repodriller.domain;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class DiffParser {
    
    public void tabsEnters() {
        String diff = getSampleDiff();

        DiffParser parsedDiff = new DiffParser(diff);

        assertOldLines(parsedDiff);
        assertNewLines(parsedDiff);
    }

    private String getSampleDiff() {
        return "diff --git a/A b/A\r\n" +
                "index 708caeb..bdc3fea 100644\r\n" +
                "--- a/A\r\n" +
                "+++ b/A\r\n" +
                "@@ -1,4 +1,17 @@\r\n" +
                " a\r\n" +
                " b\r\n" +
                "-c\r\n" +
                "+\td\r\n" +
                "+cc\r\n" +
                "+\r\n" +
                "+\r\n" +
                "+\r\n" +
                "+\r\n" +
                "+\r\n" +
                "+\r\n" +
                "+\r\n" +
                "+\r\n" +
                "+\r\n" +
                "+\r\n" +
                "+\tg\r\n" +
                "+\r\n" +
                "+j\r\n" +
                " ";
    }

    private void assertOldLines(DiffParser parsedDiff) {
        List<DiffLine> oldLines = parsedDiff.getBlocks().get(0).getLinesInOldFile();

        assertOldLine(oldLines, 1, "a");
        assertOldLine(oldLines, 2, "b");
        assertOldLine(oldLines, 3, "c");
        assertOldLine(oldLines, 4, "");
    }

    private void assertNewLines(DiffParser parsedDiff) {
        List<DiffLine> newLines = parsedDiff.getBlocks().get(0).getLinesInNewFile();

        assertNewLine(newLines, 1, "a");
        assertNewLine(newLines, 2, "b");
        assertNewLine(newLines, 3, "\td");
        assertNewLine(newLines, 4, "cc");
        assertNewLine(newLines, 5, "");
        assertNewLine(newLines, 6, "");
        assertNewLine(newLines, 7, "");
        assertNewLine(newLines, 8, "");
        assertNewLine(newLines, 9, "");
        assertNewLine(newLines, 10, "");
        assertNewLine(newLines, 11, "");
        assertNewLine(newLines, 12, "");
        assertNewLine(newLines, 13, "");
        assertNewLine(newLines, 14, "\tg");
        assertNewLine(newLines, 15, "");
        assertNewLine(newLines, 16, "j");
        assertNewLine(newLines, 17, "");
    }

    private void assertOldLine(List<DiffLine> lines, int lineNumber, String content) {
        Assert.assertTrue(lines.contains(new DiffLine(lineNumber, content, DiffLineType.KEPT)));
    }

    private void assertNewLine(List<DiffLine> lines, int lineNumber, String content) {
        Assert.assertTrue(lines.contains(new DiffLine(lineNumber, content, DiffLineType.ADDED)));
    }
}


	@Test
	public void onlyAdditions() {
		
		String diff =
				"diff --git a/A b/A\r\n"+
				"index 5ae30ef..04b86b0 100644\r\n"+
				"--- a/A\r\n"+
				"+++ b/A\r\n"+
				"@@ -2,6 +2,7 @@ aa\r\n"+
				" bb\r\n"+
				" cc\r\n"+
				" log.info(\"aa\")\r\n"+
				"+log.debug(\"b\")\r\n"+
				" dd\r\n"+
				" ee\r\n"+
				" ff";
		
		DiffParser parsedDiff = new DiffParser(diff);
		
		List<DiffLine> oldLines = parsedDiff.getBlocks().get(0).getLinesInOldFile();
		Assert.assertTrue(oldLines.contains(new DiffLine(2, "bb", DiffLineType.KEPT)));
		Assert.assertTrue(oldLines.contains(new DiffLine(3, "cc", DiffLineType.KEPT)));
		Assert.assertTrue(oldLines.contains(new DiffLine(4, "log.info(\"aa\")", DiffLineType.KEPT)));
		Assert.assertTrue(oldLines.contains(new DiffLine(5, "dd", DiffLineType.KEPT)));
		Assert.assertTrue(oldLines.contains(new DiffLine(6, "ee", DiffLineType.KEPT)));
		Assert.assertTrue(oldLines.contains(new DiffLine(7, "ff", DiffLineType.KEPT)));

		List<DiffLine> newLines = parsedDiff.getBlocks().get(0).getLinesInNewFile();
		Assert.assertTrue(newLines.contains(new DiffLine(2, "bb", DiffLineType.KEPT)));
		Assert.assertTrue(newLines.contains(new DiffLine(3, "cc", DiffLineType.KEPT)));
		Assert.assertTrue(newLines.contains(new DiffLine(4, "log.info(\"aa\")", DiffLineType.KEPT)));
		Assert.assertTrue(newLines.contains(new DiffLine(5, "log.debug(\"b\")", DiffLineType.ADDED)));
		Assert.assertTrue(newLines.contains(new DiffLine(6, "dd", DiffLineType.KEPT)));
		Assert.assertTrue(newLines.contains(new DiffLine(7, "ee", DiffLineType.KEPT)));
		Assert.assertTrue(newLines.contains(new DiffLine(8, "ff", DiffLineType.KEPT)));
	}

	@Test
	public void onlyDeletions() {
		
		String diff =
				"diff --git a/A b/A\r\n"+
						"index 5ae30ef..04b86b0 100644\r\n"+
						"--- a/A\r\n"+
						"+++ b/A\r\n"+
						"@@ -2,7 +2,6 @@ aa\r\n"+
						" bb\r\n"+
						" cc\r\n"+
						" log.info(\"aa\")\r\n"+
						"-log.debug(\"b\")\r\n"+
						" dd\r\n"+
						" ee\r\n"+
						" ff";
		
		DiffParser parsedDiff = new DiffParser(diff);
		
		List<DiffLine> oldLines = parsedDiff.getBlocks().get(0).getLinesInOldFile();
		Assert.assertTrue(oldLines.contains(new DiffLine(2, "bb", DiffLineType.KEPT)));
		Assert.assertTrue(oldLines.contains(new DiffLine(3, "cc", DiffLineType.KEPT)));
		Assert.assertTrue(oldLines.contains(new DiffLine(4, "log.info(\"aa\")", DiffLineType.KEPT)));
		Assert.assertTrue(oldLines.contains(new DiffLine(5, "log.debug(\"b\")", DiffLineType.REMOVED)));
		Assert.assertTrue(oldLines.contains(new DiffLine(6, "dd", DiffLineType.KEPT)));
		Assert.assertTrue(oldLines.contains(new DiffLine(7, "ee", DiffLineType.KEPT)));
		Assert.assertTrue(oldLines.contains(new DiffLine(8, "ff", DiffLineType.KEPT)));
		
		List<DiffLine> newLines = parsedDiff.getBlocks().get(0).getLinesInNewFile();
		Assert.assertTrue(newLines.contains(new DiffLine(2, "bb", DiffLineType.KEPT)));
		Assert.assertTrue(newLines.contains(new DiffLine(3, "cc", DiffLineType.KEPT)));
		Assert.assertTrue(newLines.contains(new DiffLine(4, "log.info(\"aa\")", DiffLineType.KEPT)));
		Assert.assertTrue(newLines.contains(new DiffLine(5, "dd", DiffLineType.KEPT)));
		Assert.assertTrue(newLines.contains(new DiffLine(6, "ee", DiffLineType.KEPT)));
		Assert.assertTrue(newLines.contains(new DiffLine(7, "ff", DiffLineType.KEPT)));
	}

	@Test
	public void additionsAndDeletions() {
		
		String diff =
				"diff --git a/A b/A\r\n"+
				"index 4624afb..870742d 100644\r\n"+
				"--- a/A\r\n"+
				"+++ b/A\r\n"+
				"@@ -1,10 +1,10 @@\r\n"+
				" aa\r\n"+
				" aaa\r\n"+
				"+xxx\r\n"+
				" bb\r\n"+
				" cc\r\n"+
				" log.info(\"aa\")\r\n"+
				" log.debug(\"b\")\r\n"+
				" dd\r\n"+
				" ee\r\n"+
				"-log.trace()\r\n"+
				" ff";
		
		DiffParser parsedDiff = new DiffParser(diff);
		
		List<DiffLine> oldLines = parsedDiff.getBlocks().get(0).getLinesInOldFile();
		Assert.assertTrue(oldLines.contains(new DiffLine(1, "aa", DiffLineType.KEPT)));
		Assert.assertTrue(oldLines.contains(new DiffLine(2, "aaa", DiffLineType.KEPT)));
		Assert.assertTrue(oldLines.contains(new DiffLine(3, "bb", DiffLineType.KEPT)));
		Assert.assertTrue(oldLines.contains(new DiffLine(4, "cc", DiffLineType.KEPT)));
		Assert.assertTrue(oldLines.contains(new DiffLine(5, "log.info(\"aa\")", DiffLineType.KEPT)));
		Assert.assertTrue(oldLines.contains(new DiffLine(6, "log.debug(\"b\")", DiffLineType.KEPT)));
		Assert.assertTrue(oldLines.contains(new DiffLine(7, "dd", DiffLineType.KEPT)));
		Assert.assertTrue(oldLines.contains(new DiffLine(8, "ee", DiffLineType.KEPT)));
		Assert.assertTrue(oldLines.contains(new DiffLine(9, "log.trace()", DiffLineType.REMOVED)));
		Assert.assertTrue(oldLines.contains(new DiffLine(10, "ff", DiffLineType.KEPT)));
		
		List<DiffLine> newLines = parsedDiff.getBlocks().get(0).getLinesInNewFile();
		Assert.assertTrue(newLines.contains(new DiffLine(1, "aa", DiffLineType.KEPT)));
		Assert.assertTrue(newLines.contains(new DiffLine(2, "aaa", DiffLineType.KEPT)));
		Assert.assertTrue(newLines.contains(new DiffLine(3, "xxx", DiffLineType.ADDED)));
		Assert.assertTrue(newLines.contains(new DiffLine(4, "bb", DiffLineType.KEPT)));
		Assert.assertTrue(newLines.contains(new DiffLine(5, "cc", DiffLineType.KEPT)));
		Assert.assertTrue(newLines.contains(new DiffLine(6, "log.info(\"aa\")", DiffLineType.KEPT)));
		Assert.assertTrue(newLines.contains(new DiffLine(7, "log.debug(\"b\")", DiffLineType.KEPT)));
		Assert.assertTrue(newLines.contains(new DiffLine(8, "dd", DiffLineType.KEPT)));
		Assert.assertTrue(newLines.contains(new DiffLine(9, "ee", DiffLineType.KEPT)));
		Assert.assertTrue(newLines.contains(new DiffLine(10, "ff", DiffLineType.KEPT)));

	}
	
	@Test
	public void tabsEnters() {
		String diff =
			"diff --git a/A b/A\r\n"+
			"index 708caeb..bdc3fea 100644\r\n"+
			"--- a/A\r\n"+
			"+++ b/A\r\n"+
			"@@ -1,4 +1,17 @@\r\n"+
			" a\r\n"+
			" b\r\n"+
			"-c\r\n"+
			"+\td\r\n"+
			"+cc\r\n"+
			"+\r\n"+
			"+\r\n"+
			"+\r\n"+
			"+\r\n"+
			"+\r\n"+
			"+\r\n"+
			"+\r\n"+
			"+\r\n"+
			"+\r\n"+
			"+\tg\r\n"+
			"+\r\n"+
			"+j\r\n"+
			" ";
		
		DiffParser parsedDiff = new DiffParser(diff);
		
		List<DiffLine> oldLines = parsedDiff.getBlocks().get(0).getLinesInOldFile();
		Assert.assertTrue(oldLines.contains(new DiffLine(1, "a", DiffLineType.KEPT)));
		Assert.assertTrue(oldLines.contains(new DiffLine(2, "b", DiffLineType.KEPT)));
		Assert.assertTrue(oldLines.contains(new DiffLine(3, "c", DiffLineType.REMOVED)));
		Assert.assertTrue(oldLines.contains(new DiffLine(4, "", DiffLineType.KEPT)));
		
		List<DiffLine> newLines = parsedDiff.getBlocks().get(0).getLinesInNewFile();
		Assert.assertTrue(newLines.contains(new DiffLine(1, "a", DiffLineType.KEPT)));
		Assert.assertTrue(newLines.contains(new DiffLine(2, "b", DiffLineType.KEPT)));
		Assert.assertTrue(newLines.contains(new DiffLine(3, "\td", DiffLineType.ADDED)));
		Assert.assertTrue(newLines.contains(new DiffLine(4, "cc", DiffLineType.ADDED)));
		Assert.assertTrue(newLines.contains(new DiffLine(5, "", DiffLineType.ADDED)));
		Assert.assertTrue(newLines.contains(new DiffLine(6, "", DiffLineType.ADDED)));
		Assert.assertTrue(newLines.contains(new DiffLine(7, "", DiffLineType.ADDED)));
		Assert.assertTrue(newLines.contains(new DiffLine(8, "", DiffLineType.ADDED)));
		Assert.assertTrue(newLines.contains(new DiffLine(9, "", DiffLineType.ADDED)));
		Assert.assertTrue(newLines.contains(new DiffLine(10, "", DiffLineType.ADDED)));
		Assert.assertTrue(newLines.contains(new DiffLine(11, "", DiffLineType.ADDED)));
		Assert.assertTrue(newLines.contains(new DiffLine(12, "", DiffLineType.ADDED)));
		Assert.assertTrue(newLines.contains(new DiffLine(13, "", DiffLineType.ADDED)));
		Assert.assertTrue(newLines.contains(new DiffLine(14, "\tg", DiffLineType.ADDED)));
		Assert.assertTrue(newLines.contains(new DiffLine(15, "", DiffLineType.ADDED)));
		Assert.assertTrue(newLines.contains(new DiffLine(16, "j", DiffLineType.ADDED)));
		Assert.assertTrue(newLines.contains(new DiffLine(17, "", DiffLineType.KEPT)));
	}
	
	String diff = getSampleDiff();

        DiffParser parsedDiff = new DiffParser(diff);

        assertDiffBlock(parsedDiff, 0, 72, 78, "GitRepository.java", "GitRepository2.java");
        assertDiffBlock(parsedDiff, 1, 155, 161, "getHead()", "getHead2()");
        assertDiffBlock(parsedDiff, 2, 320, 325, "", "newline");
    }

    private String getSampleDiff() {
        return "diff --git a/GitRepository.java b/GitRepository.java\r\n" +
                "index f38a97d..2b96b0e 100644\r\n" +
                "--- a/GitRepository.java\r\n" +
                "+++ b/GitRepository.java\r\n" +
                "@@ -72,7 +72,7 @@ public class GitRepository implements SCM {\r\n" +
                " \r\n" +
                "        private static Logger log = Logger.getLogger(GitRepository.class);\r\n" +
                " \r\n" +
                "-       public GitRepository(String path) {\r\n" +
                "+       public GitRepository2(String path) {\r\n" +
                "                this.path = path;\r\n" +
                "                this.maxNumberFilesInACommit = checkMaxNumberOfFiles();\r\n" +
                "                this.maxSizeOfDiff = checkMaxSizeOfDiff();\r\n" +
                "@@ -155,7 +155,7 @@ public class GitRepository implements SCM {\r\n" +
                "                return git.getRepository().getBranch();\r\n" +
                "        }\r\n" +
                " \r\n" +
                "-       public ChangeSet getHead() {\r\n" +
                "+       public ChangeSet getHead2() {\r\n" +
                "                Git git = null;\r\n" +
                "                try {\r\n" +
                "                        git = openRepository();\r\n" +
                "@@ -320,6 +320,7 @@ public class GitRepository implements SCM {\r\n" +
                " \r\n" +
                "                return diffs;\r\n" +
                "        }\r\n" +
                "+       newline\r\n" +
                " \r\n" +
                "        private void setContext(DiffFormatter df) {\r\n" +
                "                String context = System.getProperty(\"git.diffcontext\");";
    }

    private void assertDiffBlock(DiffParser parsedDiff, int blockIndex, int startLine, int endLine, String oldMethodName, String newMethodName) {
        List<DiffLine> oldLinesBlock = parsedDiff.getBlocks().get(blockIndex).getLinesInOldFile();
        List<DiffLine> newLinesBlock = parsedDiff.getBlocks().get(blockIndex).getLinesInNewFile();

        for (int i = startLine; i <= endLine; i++) {
            assertOldAndNewLines(oldLinesBlock, newLinesBlock, i, oldMethodName, newMethodName);
        }
    }

    private void assertOldAndNewLines(List<DiffLine> oldLines, List<DiffLine> newLines, int lineNumber, String oldMethodName, String newMethodName) {
        Assert.assertTrue(oldLines.contains(new DiffLine(lineNumber, getOldLineContent(lineNumber, oldMethodName), DiffLineType.KEPT)));
        Assert.assertTrue(newLines.contains(new DiffLine(lineNumber, getNewLineContent(lineNumber, newMethodName), DiffLineType.ADDED)));
    }

    private String getOldLineContent(int lineNumber, String methodName) {
        return lineNumber == 75 ? methodName + "(String path) {" : "";
    }

    private String getNewLineContent(int lineNumber, String methodName) {
        return lineNumber == 75 ? methodName + "(String path) {" : "";
    }
}
