package com.example.vdllo.mirror.bean;

import java.util.List;

/**
 * Created by dllo on 16/4/6.
 */
public class MenuListBean {

    /**
     * result : 1
     * msg :
     * data : {"color_data":{"default_font_color":"#496383","Select_font_color":"#ffffff","menu_topColor":"#0a1f3a","menu_buttomColor":"#114282"},"list":[{"title":"全部分類","topColor":"#0a1f3a","buttomColor":"#114282","type":"6","info_data":"","store":"10"},{"title":"瀏覽平光鏡","topColor":"#0a1f3a","buttomColor":"#114282","type":"3","info_data":"269","store":"9"},{"title":"瀏覽太陽鏡","topColor":"#0a1f3a","buttomColor":"#114282","type":"3","info_data":"268","store":"8"},{"title":"我的購物車","topColor":"#0a1f3a","buttomColor":"#114282","type":"4","info_data":"","store":"5"}]}
     */

    private String result;
    private String msg;
    /**
     * color_data : {"default_font_color":"#496383","Select_font_color":"#ffffff","menu_topColor":"#0a1f3a","menu_buttomColor":"#114282"}
     * list : [{"title":"全部分類","topColor":"#0a1f3a","buttomColor":"#114282","type":"6","info_data":"","store":"10"},{"title":"瀏覽平光鏡","topColor":"#0a1f3a","buttomColor":"#114282","type":"3","info_data":"269","store":"9"},{"title":"瀏覽太陽鏡","topColor":"#0a1f3a","buttomColor":"#114282","type":"3","info_data":"268","store":"8"},{"title":"我的購物車","topColor":"#0a1f3a","buttomColor":"#114282","type":"4","info_data":"","store":"5"}]
     */

    private DataEntity data;

    public void setResult(String result) {
        this.result = result;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * default_font_color : #496383
         * Select_font_color : #ffffff
         * menu_topColor : #0a1f3a
         * menu_buttomColor : #114282
         */

        private ColorDataEntity color_data;
        /**
         * title : 全部分類
         * topColor : #0a1f3a
         * buttomColor : #114282
         * type : 6
         * info_data :
         * store : 10
         */

        private List<ListEntity> list;

        public void setColor_data(ColorDataEntity color_data) {
            this.color_data = color_data;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public ColorDataEntity getColor_data() {
            return color_data;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ColorDataEntity {
            private String default_font_color;
            private String Select_font_color;
            private String menu_topColor;
            private String menu_buttomColor;

            public void setDefault_font_color(String default_font_color) {
                this.default_font_color = default_font_color;
            }

            public void setSelect_font_color(String Select_font_color) {
                this.Select_font_color = Select_font_color;
            }

            public void setMenu_topColor(String menu_topColor) {
                this.menu_topColor = menu_topColor;
            }

            public void setMenu_buttomColor(String menu_buttomColor) {
                this.menu_buttomColor = menu_buttomColor;
            }

            public String getDefault_font_color() {
                return default_font_color;
            }

            public String getSelect_font_color() {
                return Select_font_color;
            }

            public String getMenu_topColor() {
                return menu_topColor;
            }

            public String getMenu_buttomColor() {
                return menu_buttomColor;
            }
        }

        public static class ListEntity {
            private String title;
            private String topColor;
            private String buttomColor;
            private String type;
            private String info_data;
            private String store;

            public void setTitle(String title) {
                this.title = title;
            }

            public void setTopColor(String topColor) {
                this.topColor = topColor;
            }

            public void setButtomColor(String buttomColor) {
                this.buttomColor = buttomColor;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setInfo_data(String info_data) {
                this.info_data = info_data;
            }

            public void setStore(String store) {
                this.store = store;
            }

            public String getTitle() {
                return title;
            }

            public String getTopColor() {
                return topColor;
            }

            public String getButtomColor() {
                return buttomColor;
            }

            public String getType() {
                return type;
            }

            public String getInfo_data() {
                return info_data;
            }

            public String getStore() {
                return store;
            }
        }
    }
}
