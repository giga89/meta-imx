require imx-gpu-viv-v6.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=1b4db4b25c3a1e422c0c0ed64feb65d2"

SRC_URI[md5sum] = "6e5d86f61d13562f60358171c9493e1e"
SRC_URI[sha256sum] = "f84019c27061dde2465684857013b9d4632a8d9496fadf4228d8d9bff6d220c6"

do_install_append() {
    rm ${D}${libdir}/libGL.so.1.2.0
    cp ${S}/gpu-core/usr/lib/libGL-${backend}.so ${D}${libdir}/libGL.so.1.2.0

    # Remove the work done by the upstream install for libvulkan so
    # we can install the newly versioned library files
    rm ${D}${libdir}/libvulkan_VSI.so
    # Copy backend-specific library and rename it for use by vulkan-loader
    # library of the same name
    for i in fb wl x11; do
        if [ "$i" = "${backend}" ]; then
            mv ${D}${libdir}/libvulkan-${backend}.so.1.1.3 ${D}${libdir}/libvulkan_VSI.so.1.1.3
        else
            rm ${D}${libdir}/libvulkan-${i}.so.1.1.3
        fi
    done
    patchelf --set-soname libvulkan_VSI.so.1 ${D}${libdir}/libvulkan_VSI.so.1.1.3
    rm ${D}${libdir}/libvulkan.so.1 ${D}${libdir}/libvulkan.so
    ln -s libvulkan_VSI.so.1.1.3 ${D}${libdir}/libvulkan_VSI.so.1
    ln -s libvulkan_VSI.so.1.1.3 ${D}${libdir}/libvulkan_VSI.so
}

COMPATIBLE_MACHINE = "(mx8)"
