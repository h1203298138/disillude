// /**
//  * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
//  * <p>
//  * 项目名： hua-project
//  * 文件名： AttachmentUtil.java
//  * 模块说明：
//  * 修改历史：
//  * 2023年09月02日 - Hedh - 创建。
//  */
// package com.hua.disillude.template.oss;
//
// import com.hua.disillude.biz.spring.ServiceFactory;
// import com.hua.disillude.mini.datamodel.Attachment;
// import com.hua.disillude.mini.entity.hasinfo.HasAttachment;
// import org.springframework.core.env.Environment;
//
// import java.text.MessageFormat;
// import java.util.ArrayList;
// import java.util.HashSet;
// import java.util.List;
// import java.util.Objects;
//
// /**
//  * @author Hedh
//  * @since 1.0
//  */
// public class AttachmentUtil {
//   public static final String TEMP_OF_1_DAY = "tempOf1Day";
//   public static final String TEMP_OF_7_DAY = "tempOf7Day";
//   public static final String TEMP_OF_30_DAY = "tempOf30Day";
//
//   public static <T> T genuine(String tenant, String refType, String refId, T item) {
//     return genuine(tenant, refType, refId, item, null);
//   }
//
//   public static <T> T genuine(String tenant, String refType, String refId, T item, T sourceItem) {
//     Bucket bucket = getBucket();
//     String appId = getEnv().getProperty("spring.application.name");
//     Attachment target;
//     if (item instanceof Attachment) {
//       target = (Attachment) item;
//     } else if (item instanceof HasAttachment) {
//       target = ((HasAttachment) item).getAttachment();
//     } else {
//       return null;
//     }
//
//     if (target.isTemporary()) {
//       String path = refType + "/" + refId + "/" + Iid.next();
//       target.setTemporary(false);
//       target.setKey(bucket.copy(target.getKey(), MessageFormat.format("{0}/{1}/{2}/{3}", appId, tenant, path, target.getName())));
//       target.setUrl(bucket.getUrl(target.getKey()));
//     }
//     Attachment source = null;
//     if (sourceItem instanceof Attachment) {
//       source = (Attachment) sourceItem;
//     } else if (sourceItem instanceof HasAttachment) {
//       source = ((HasAttachment) item).getAttachment();
//     }
//
//     if (source != null && Objects.equals(target.getKey(), source.getKey()) == false) {
//       bucket.delete(source.getKey());
//     }
//     return item;
//   }
//
//   public static <T> List<T> genuine(String tenant, String refType, String refId, List<T> list) {
//     return genuine(tenant, refType, refId, list, new ArrayList<>());
//   }
//
//   public static <T> List<T> genuine(String tenant, String refType, String refId, List<T> list, List<T> sourceList) {
//     if (list == null) {
//       return new ArrayList<>();
//     }
//
//     Bucket bucket = getBucket();
//     String appId = getEnv().getProperty("spring.application.name");
//     List<T> result = new ArrayList<>();
//     HashSet<String> keys = new HashSet<>();
//     for (T item : list) {
//       Attachment target;
//       if (item instanceof Attachment) {
//         target = (Attachment) item;
//       } else if (item instanceof HasAttachment) {
//         target = ((HasAttachment) item).getAttachment();
//       } else {
//         continue;
//       }
//
//       if (target.isTemporary()) {
//         String path = refType + "/" + refId + "/" + Iid.next();
//         target.setTemporary(false);
//         target.setKey(bucket.copy(target.getKey(), MessageFormat.format("{0}/{1}/{2}/{3}", appId, tenant, path, target.getName())));
//         target.setUrl(bucket.getUrl(target.getKey()));
//       }
//       keys.add(target.getKey());
//       result.add(item);
//     }
//
//     if (sourceList != null) {
//       for (T item : sourceList) {
//         Attachment target;
//         if (item instanceof Attachment) {
//           target = (Attachment) item;
//         } else if (item instanceof HasAttachment) {
//           target = ((HasAttachment) item).getAttachment();
//         } else {
//           continue;
//         }
//
//         if (keys.contains(target.getKey()) == false) {
//           bucket.delete(target.getKey());
//         }
//       }
//     }
//     return result;
//   }
//
//   public static <T> void delete(String tenant, List<T> list) {
//     assert tenant != null;
//
//     Bucket bucket = getBucket();
//     String appId = getEnv().getProperty("spring.application.name");
//     for (T item : list) {
//       Attachment target;
//       if (item instanceof Attachment) {
//         target = (Attachment) item;
//       } else if (item instanceof HasAttachment) {
//         target = ((HasAttachment) item).getAttachment();
//       } else {
//         continue;
//       }
//
//       bucket.delete(target.getKey());
//     }
//   }
//
//   protected static Bucket getBucket() {
//     return ServiceFactory.getService(Bucket.class);
//   }
//
//   protected static Environment getEnv() {
//     return ServiceFactory.getService(Environment.class);
//   }
// }
