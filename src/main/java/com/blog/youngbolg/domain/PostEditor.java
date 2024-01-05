package com.blog.youngbolg.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostEditor {

    private String title;
    private String content;

    @Builder
    public PostEditor(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static PostEditor.PostEditorBuilder builder() {
        return new PostEditor.PostEditorBuilder();
    }

    public static class PostEditorBuilder {
        private String title;
        private String content;

        public PostEditorBuilder() {
        }

        public PostEditor.PostEditorBuilder title(final String title) {
            if(title != null) {
                this.title = title;
            }
            return this;
        }

        public PostEditor.PostEditorBuilder content(final String content) {
            if(content != null) {
                this.content = content;
            }
            return this;
        }

        public Post build() {
            return new Post(this.title, this.content);
        }

        public String toString() {
            return "Post.PostBuilder(title=" + this.title + ", content=" + this.content + ")";
        }
    }

}
