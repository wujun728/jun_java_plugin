package org.smartboot.service.api.permission;

import org.smartboot.shared.SmartException;

public class PermissionUtil {

		public static boolean hasPermission(String[] permissions, Permission need) {

			switch (need.PermissionRelation()) {
			case OR:
				for (PermissionEnum cfgPerm : need.value()) {
					for (String p : permissions) {
						if (cfgPerm.getCode().equals(p)) {
							return true;
						}
					}
				}
				for (String cfgPerm : need.ExtendedPermision()) {
					for (String p : permissions) {
						if (p.equals(cfgPerm)) {
							return true;
						}
					}
				}
				return false;
			case AND:
				for (PermissionEnum cfgPerm : need.value()) {
					boolean has = false;
					for (String p : permissions) {
						if (cfgPerm.getCode().equals(p)) {
							has = true;
							break;
						}
					}
					if (!has) {
						return false;
					}
				}
				for (String cfgPerm : need.ExtendedPermision()) {
					boolean has = false;
					for (String p : permissions) {
						if (p.equals(cfgPerm)) {
							has = true;
							break;
						}
					}
					if (!has) {
						return false;
					}
				}
				return true;
			case NOT:
				for (String p : permissions) {
					for (PermissionEnum cfgPerm : need.value()) {
						if (p.equals(cfgPerm)) {
							return false;
						}
					}
				}
				for (String p : permissions) {
					for (String cfgPerm : need.ExtendedPermision()) {
						if (p.equals(cfgPerm)) {
							return false;
						}
					}
				}
				return true;
			default:
				throw new SmartException("unsupport relation " + need.PermissionRelation());
			}

		}

	}