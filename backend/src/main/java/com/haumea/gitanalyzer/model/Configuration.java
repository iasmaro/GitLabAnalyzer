package com.haumea.gitanalyzer.model;

import com.haumea.gitanalyzer.gitlab.CommentType;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.*;

public class Configuration {
    private String fileName;
    private Date start;
    private Date end;
    private String targetBranch;
    private Map<String, Float> editFactor;
    private Map<String, Float> fileFactor;
    private List<String> ignoreFileExtension;
    private Map<String, List<CommentType>> commentTypes;

    private static final CommentType singleDoubleSlash = new CommentType("//", "");
    private static final CommentType singleHash = new CommentType("#", "");
    private static final CommentType multiSlashStar = new CommentType("/*", "*/");
    private static final CommentType multiTripleDoubleQuote = new CommentType("\"\"\"", "\"\"\"");
    private static final CommentType multiTripleSingleQuote = new CommentType("'''", "'''");
    private static final CommentType multiArrow = new CommentType("<!--", "-->");

    public Configuration() {
        super();
    }

    @PersistenceConstructor
    public Configuration(String fileName, Date start, Date end,
                         String targetBranch, Map<String, Float> editFactor,
                         Map<String, Float> fileFactor, List<String> ignoreFileExtension,
                         Map<String, List<CommentType>> commentTypes) {
        this.fileName = fileName;
        this.start = start;
        this.end = end;
        this.targetBranch = targetBranch;
        this.editFactor = editFactor;
        this.fileFactor = fileFactor;
        this.ignoreFileExtension = ignoreFileExtension;
        this.commentTypes = commentTypes;
    }

    public Configuration(Date start){

        this.fileName = "default";
        this.start = start;
        this.end = new Date();
        this.targetBranch = "master";

        Map<String, Float> editFactor = new HashMap<String, Float>();
        List<String> edits = new ArrayList<>(Arrays.asList("addLine", "deleteLine", "syntaxLine", "moveLine"));
        List<Float> editFactors = new ArrayList<>(Arrays.asList(1.0f, 0.2f, 0.2f, 0.5f));
        for(int i = 0; i < edits.size(); i++){
            editFactor.put(edits.get(i), editFactors.get(i));
        }
        this.editFactor = editFactor;

        Map<String, Float> fileFactor = new HashMap<>();
        List<String> files = new ArrayList<>(Arrays.asList(
                "java", "js", "ts", "py", "html", "css", "xml",
                "c", "h", "cpp", "hpp", "cxx", "hxx"));
        for(int i = 0; i < files.size(); i++){
            fileFactor.put(files.get(i), 1.0f);
        }
        this.fileFactor = fileFactor;

        this.ignoreFileExtension = new ArrayList<>();

        Map<String, List<CommentType>> commentTypes = new HashMap<>();
        commentTypes.put("java", new ArrayList<CommentType>(Arrays.asList(singleDoubleSlash, multiSlashStar)));
        commentTypes.put("js", new ArrayList<CommentType>(Arrays.asList(singleDoubleSlash, multiSlashStar)));
        commentTypes.put("ts", new ArrayList<CommentType>(Arrays.asList(singleDoubleSlash, multiSlashStar)));
        commentTypes.put("py", new ArrayList<CommentType>(Arrays.asList(singleHash, multiTripleDoubleQuote, multiTripleSingleQuote)));
        commentTypes.put("html", new ArrayList<CommentType>(Arrays.asList(multiArrow)));
        commentTypes.put("css", new ArrayList<CommentType>(Arrays.asList(multiSlashStar)));
        commentTypes.put("xml", new ArrayList<CommentType>(Arrays.asList(multiArrow)));
        commentTypes.put("c", new ArrayList<CommentType>(Arrays.asList(singleDoubleSlash, multiSlashStar)));
        commentTypes.put("h", new ArrayList<CommentType>(Arrays.asList(singleDoubleSlash, multiSlashStar)));
        commentTypes.put("cpp", new ArrayList<CommentType>(Arrays.asList(singleDoubleSlash, multiSlashStar)));
        commentTypes.put("hpp", new ArrayList<CommentType>(Arrays.asList(singleDoubleSlash, multiSlashStar)));
        commentTypes.put("cxx", new ArrayList<CommentType>(Arrays.asList(singleDoubleSlash, multiSlashStar)));
        commentTypes.put("hxx", new ArrayList<CommentType>(Arrays.asList(singleDoubleSlash, multiSlashStar)));
        this.commentTypes = commentTypes;

    }

    public String getFileName(){
        return fileName;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public String getTargetBranch() {
        return targetBranch;
    }

    public Map<String, Float> getEditFactor(){
        return editFactor;
    }

    public Map<String, Float> getFileFactor(){
        return fileFactor;
    }

    public List<String> getIgnoreFileExtension(){
        return ignoreFileExtension;
    }

    public Map<String, List<CommentType>> getCommentTypes() {
        return commentTypes;
    }

}
