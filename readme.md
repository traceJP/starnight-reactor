# 星空在线考试平台 （Star Night）

## 一、项目介绍

星空在线考试平台是一个专注于在线考试的平台，为在校师生提供稳定高效的在线考试服务，支持多种题型：选择题、多选题、判断题、填空题、简答题、富文本编辑、拍照答题（智能识别），支持多种答卷批改方式：系统预批改、管理员手动批改、文本分析智能批改，提供学生智能错题解析等功能。包含PC端、移动端、微信小程序。采用前后端分离设计，部署方便，扩展性强，界面设计友好，代码结构清晰。

- 后端项目：[StarNight](https://github.com/traceJP/StarNight)
- 基于 WebFlux 的后端重构项目：[StarNight-Reactor](https://github.com/traceJP/starnight-reactor)
- 前端项目：[StarNight-Vue](https://github.com/traceJP/StarNight-Vue)

## 基于 WebFlux 的后端重构项目

### 介绍

本项目是原星空在线考试平台后端的 全新技术栈 重构版，基于 JDK17 下的完全 Spring WebFlux 非阻塞式模型编写，采用 SpringBoot3 + R2DBC，集成引入 Reactive-Redis、Reactive-ElasticSearch、Reactive-Security，数据库使用 Postgres。项目前端完全兼容原项目前端，可直接使用，无感替换。

### 关于

- 目前此项目，基础框架已经完全搭建完毕，后续只剩下业务未开发
- 相关业务参考原后端项目
- 目前作者暂无打算维护此项目，欢迎各位大大贡献源码
