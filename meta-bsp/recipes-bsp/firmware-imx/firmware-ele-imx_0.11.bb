# Copyright 2021-2023 NXP
SUMMARY = "NXP i.MX ELE firmware"
DESCRIPTION = "EdgeLock Enclave firmware for i.MX series SoCs"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=db4762b09b6bda63da103963e6e081de" 

inherit fsl-eula-unpack use-imx-security-controller-firmware deploy

SRC_URI = "${FSL_MIRROR}/${BP}.bin;fsl-eula=true"
SRC_URI[md5sum] = "b804ea829789df088887c4779fa46d6f"
SRC_URI[sha256sum] = "7cea5d2e762629ecfd5c9d6d7630b6bfa2a81c5c1a4c837effefc6b7442c05de"

do_compile[noexec] = "1"

do_install() {
   install -d ${D}${nonarch_base_libdir}/firmware/imx/ele
   install -m 0644 ${S}/${SECO_FIRMWARE_NAME} ${D}${nonarch_base_libdir}/firmware/imx/ele
}

do_deploy () {
    # Deploy the related firmware to be package by imx-boot
    install -m 0644 ${S}/${SECO_FIRMWARE_NAME}  ${DEPLOYDIR}
}
addtask deploy after do_install before do_build

FILES:${PN} += "${nonarch_base_libdir}/firmware/imx/ele/${SECO_FIRMWARE_NAME}"

COMPATIBLE_MACHINE = "(mx8ulp-nxp-bsp|mx9-nxp-bsp)"