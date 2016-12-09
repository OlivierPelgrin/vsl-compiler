.rdata
.align 2
$LC0:
       .ascii	"%d\000"
.text
# label main
# beginfunc 
	.align	2
	.globl main
	.ent main
main:
	 addiu	$sp,$sp,-72
	 sw	$31,16($29)
	 sw	$16,24($29)
	 sw	$17,28($29)
	 sw	$18,32($29)
	 sw	$19,36($29)
	 sw	$20,40($29)
	 sw	$21,44($29)
	 sw	$22,48($29)
	 sw	$23,52($29)
# var a
# var b
# var c
# a =  call L8
       addiu   $5,$29,56
       lui   $4,%hi($LC0)
       addiu   $4,$4,%lo($LC0)
	 jal   n_read_int
	 nop
       sw   $2,56($29)
# b =  call L8
       sw  $2,56($29)
       addiu   $5,$29,60
       lui   $4,%hi($LC0)
       addiu   $4,$4,%lo($LC0)
	 jal   n_read_int
	 nop
       sw   $2,60($29)
# var T_0
# arg a
       lw  $8,56($29)
	 sw   $8,0($29)
	move   $4,$8
# arg b
       move  $9,$2
	 sw   $9,4($29)
	move   $5,$9
# T_0 =  call compute
       sw  $2,60($29)
       sw  $9,60($29)
	 jal   compute
	 nop
# c = T_0
       move  $8,$2
# endfunc 
	 lw	$16,20($29)
	 lw	$17,24($29)
	 lw	$18,28($29)
	 lw	$19,32($29)
	 lw	$20,36($29)
	 lw	$21,40($29)
	 lw	$22,44($29)
	 lw	$23,48($29)
	 lw	$31,16($29)
	 addiu	$sp,$sp,72
	 jr  $31
	 nop
	.end main
# label compute
# beginfunc 
	.align	2
	.globl compute
	.ent compute
compute:
	 addiu	$sp,$sp,-40
	 sw	$31,16($29)
# var i
# i = 0
       li   $8, 0
# label L12
       sw  $8,24($29)
L12:
# var T_1
# T_1 = i - 100
       lw  $9,24($29)
       li   $8, 100
	sub $8, $9, $8
# ifz T_1 goto L13
       sw  $8,28($29)
	beq  $8,0,L13
# var T_2
# T_2 = a - b
       lw  $11,0($29)
       lw  $10,0($29)
	sub $10, $11, $10
# ifz T_2 goto L10
       sw  $10,32($29)
	beq  $10,0,L10
# a = b
       lw  $12,0($29)
# goto L11
       sw  $12,0($29)
	j  L11
# label L10
L10:
# b = a
       lw  $8,0($29)
# label L11
       sw  $8,0($29)
L11:
# var T_3
# T_3 = i + 1
       lw  $9,24($29)
       li   $8, 1
	add $8, $9, $8
# i = T_3
       sw  $8,36($29)
# arg i
       sw  $8,24($29)
	 sw   $8,0($29)
	move   $4,$8
# call L2
       move $5,$4
       lui   $4,%hi($LC0)
       addiu   $4,$4,%lo($LC0)
	 jal   n_printf
	 nop
# goto L12
	j  L12
# label L13
L13:
# return a
       lw  $2,0($29)
	 lw	$31,16($29)
	 addiu	$sp,$sp,40
	 jr  $31
	 nop
# endfunc 
	 lw	$31,16($29)
	 addiu	$sp,$sp,40
	 jr  $31
	 nop
	.end compute
