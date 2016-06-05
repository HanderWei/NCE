# NCE

## App 基本要求
这是一个简易的分级阅读程序， 基本功能是：
1. 打开后看到文章列表（已完成）
2. 点击列表某项，打开文章（已完成）
3. 文章界面有一个按钮，点击则在文章中高亮在单词列表中出现的单词（已完成）
4. 文章内容实现两边对齐；（***）
5. 单词实现点击高亮；（已完成）

其他：
1. 保存TextView的状态（横竖屏切换）


譬如 nce4_words 的内容如下：
单词              级别
compare         3
backward        2
technology      2
alien                1

文本内容是
Compared with the alien, our technology is backward。
如果 slide-bar 为 2， 那么只高亮级别在 2 及以下的词，包括 backward, technology,
alien；如果 slide-bar 为 3，那么高亮级别小于等于 3 的词。