package com.khane.customview

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.khane.R
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import kotlin.math.*

class CircleSeekBar : View {
    /**
     * Current point value.
     */
    private var mProgressDisplay = MIN

    /**
     * The min value of progress value.
     */
    var mMin = MIN

    /**
     * The maximum value that can be set.
     */
    var mMax = MAX

    /**
     * The increment/decrement value for each movement of progress.
     */
    var step = 1
    var mArcWidth = 8
    var mProgressWidth = 12
    //
    // internal variables
    //
    /**
     * The counts of point update to determine whether to change previous progress.
     */
    private var mUpdateTimes = 0
    private var mPreviousProgress = -1f
    var currentProgress = 0f
        private set

    /**
     * Determine whether reach max of point.
     */
    private var isMax = false

    /**
     * Determine whether reach min of point.
     */
    private var isMin = false

    // For Arc
    private val mArcRect = RectF()
    private var mArcPaint: Paint? = null

    // For Progress
    private var mProgressPaint: Paint? = null
    private var mProgressSweep = 0f

    //For Text progress
    private var mTextPaint: Paint? = null
    var mTextSize = TEXT_SIZE_DEFAULT
    private val mTextRect = Rect()
    var mIsShowText = true
    private var mCenterX = 0
    private var mCenterY = 0
    private var mCircleRadius = 0

    /**
     * The drawable for circle indicator of Seekbar
     */
    var mThumbDrawable: Drawable? = null

    // Coordinator (X, Y) of Indicator icon
    private var mThumbX = 0
    private var mThumbY = 0
    private var mThumbSize = 0
    private var mPadding = 0
    var angle = 0.0
        private set
    private var mIsThumbSelected = false
    private var mOnSeekBarChangeListener: OnSeekBarChangedListener? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    fun setProgressDisplayAndInvalidate(progressDisplay: Int) {
        this.progressDisplay = progressDisplay
        if (mOnSeekBarChangeListener != null) {
            mOnSeekBarChangeListener?.onPointsChanged(this, mProgressDisplay, false)
        }
        invalidate()
    }

    var progressDisplay: Int
        get() = mProgressDisplay
        set(progressDisplay) {
            mProgressDisplay = progressDisplay
            mProgressDisplay = if (mProgressDisplay > mMax) mMax else mProgressDisplay
            mProgressDisplay = if (mProgressDisplay < mMin) mMin else mProgressDisplay
            mProgressSweep = mProgressDisplay.toFloat() / valuePerDegree()
            angle = Math.PI / 2 - mProgressSweep * Math.PI / 180
        }

    private fun init(context: Context, attrs: AttributeSet?) {
        val density = context.resources.displayMetrics.density
        var progressColor = ContextCompat.getColor(context,R.color.colorBlack)
        var arcColor = ContextCompat.getColor(context, R.color.colorBlack)
        var textColor = ContextCompat.getColor(context, R.color.colorBlack)
        mProgressWidth = (density * mProgressWidth).toInt()
        mArcWidth = (density * mArcWidth).toInt()
        mTextSize = (density * mTextSize).toInt()
//        mThumbDrawable = ContextCompat.getDrawable(context, R.drawable.thumb_circle_breathe)
        mThumbDrawable= ColorDrawable(Color.BLACK)
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleSeekBar, 0, 0)
            val indicator = typedArray.getDrawable(R.styleable.CircleSeekBar_csb_thumbDrawable)
            if (indicator != null) mThumbDrawable = indicator
            mProgressDisplay =
                typedArray.getInteger(R.styleable.CircleSeekBar_csb_progress, mProgressDisplay)
            mThumbSize =
                typedArray.getDimensionPixelSize(R.styleable.CircleSeekBar_csb_thumbSize, 50)
            mMin = typedArray.getInteger(R.styleable.CircleSeekBar_csb_min, mMin)
            mMax = typedArray.getInteger(R.styleable.CircleSeekBar_csb_max, mMax)
            step = typedArray.getInteger(R.styleable.CircleSeekBar_csb_step, step)
            mTextSize =
                typedArray.getDimension(R.styleable.CircleSeekBar_csb_textSize, mTextSize.toFloat())
                    .toInt()
            textColor = typedArray.getColor(R.styleable.CircleSeekBar_csb_textColor, textColor)
            mIsShowText =
                typedArray.getBoolean(R.styleable.CircleSeekBar_csb_isShowText, mIsShowText)
            mProgressWidth = typedArray.getDimension(
                R.styleable.CircleSeekBar_csb_progressWidth,
                mProgressWidth.toFloat()
            )
                .toInt()
            progressColor =
                typedArray.getColor(R.styleable.CircleSeekBar_csb_progressColor, progressColor)
            mArcWidth =
                typedArray.getDimension(R.styleable.CircleSeekBar_csb_arcWidth, mArcWidth.toFloat())
                    .toInt()
            arcColor = typedArray.getColor(R.styleable.CircleSeekBar_csb_arcColor, arcColor)
            mPadding = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                val all =
                    paddingLeft + paddingRight + paddingBottom + paddingTop + paddingEnd + paddingStart
                all / 6
            } else {
                (paddingLeft + paddingRight + paddingBottom + paddingTop) / 4
            }
            typedArray.recycle()
        }

        // range check
        mProgressDisplay = if (mProgressDisplay > mMax) mMax else mProgressDisplay
        mProgressDisplay = if (mProgressDisplay < mMin) mMin else mProgressDisplay
        mProgressSweep = mProgressDisplay.toFloat() / valuePerDegree()
        angle = Math.PI / 2 - mProgressSweep * Math.PI / 180
        currentProgress = (mProgressSweep * valuePerDegree()).roundToLong().toFloat()
        mArcPaint = Paint()
        mArcPaint?.color = arcColor
        mArcPaint?.isAntiAlias = true
        mArcPaint?.style = Paint.Style.STROKE
        mArcPaint?.strokeWidth = mArcWidth.toFloat()
        mProgressPaint = Paint()
        mProgressPaint?.color = progressColor
        mProgressPaint?.isAntiAlias = true
        mProgressPaint?.isDither = true
        mProgressPaint?.style = Paint.Style.STROKE
        mProgressPaint?.strokeJoin = Paint.Join.ROUND
        mProgressPaint?.strokeCap = Paint.Cap.ROUND
        mProgressPaint?.pathEffect = CornerPathEffect(mProgressWidth.toFloat())
        mProgressPaint?.strokeWidth = mProgressWidth.toFloat()
        mTextPaint = Paint()
        mTextPaint?.color = textColor
        mTextPaint?.isAntiAlias = true
        mTextPaint?.style = Paint.Style.FILL
        mTextPaint?.textSize = mTextSize.toFloat()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val min = w.coerceAtMost(h)

        // find circle's rectangle points
        val alignLeft = (w - min) / 2
        val alignTop = (h - min) / 2
        val alignRight = alignLeft + min
        val alignBottom = alignTop + min

        // save circle coordinates
        mCenterX = alignRight / 2 + (w - alignRight) / 2
        mCenterY = alignBottom / 2 + (h - alignBottom) / 2
        val progressDiameter = (min - mPadding).toFloat()
        mCircleRadius = (progressDiameter / 2).toInt()
        val top = h / 2 - progressDiameter / 2
        val left = w / 2 - progressDiameter / 2
        mArcRect[left, top, left + progressDiameter] = top + progressDiameter
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        if (mIsShowText) {
            // draw the text
            val textPoint = mProgressDisplay.toString()
            mTextPaint?.getTextBounds(textPoint, 0, textPoint.length, mTextRect)
            // center the text
            val xPos = canvas.width / 2 - mTextRect.width() / 2
            val yPos =
                (mArcRect.centerY() - (mTextPaint!!.descent() + mTextPaint!!.ascent()) / 2).toInt()
            canvas.drawText(
                mProgressDisplay.toString(),
                xPos.toFloat(),
                yPos.toFloat(),
                mTextPaint!!
            )
        }

        // draw the arc and progress
        canvas.drawCircle(
            mCenterX.toFloat(),
            mCenterY.toFloat(),
            mCircleRadius.toFloat(),
            mArcPaint!!
        )
        canvas.drawArc(mArcRect, ANGLE_OFFSET.toFloat(), mProgressSweep, false, mProgressPaint!!)

        // find thumb position
        mThumbX = (mCenterX + mCircleRadius * cos(angle)).toInt()
        mThumbY = (mCenterY - mCircleRadius * sin(angle)).toInt()
        mThumbDrawable?.setBounds(
            mThumbX - mThumbSize / 2, mThumbY - mThumbSize / 2,
            mThumbX + mThumbSize / 2, mThumbY + mThumbSize / 2
        )
        mThumbDrawable?.draw(canvas)
    }

    private fun valuePerDegree(): Float {
        return mMax / 360.0f
    }

    /**
     * Invoked when slider starts moving or is currently moving. This method calculates and sets position and angle of the thumb.
     *
     * @param touchX Where is the touch identifier now on X axis
     * @param touchY Where is the touch identifier now on Y axis
     */
    private fun updateProgressState(touchX: Int, touchY: Int) {
        val distanceX = touchX - mCenterX
        val distanceY = mCenterY - touchY
        val c = sqrt(distanceX.toDouble().pow(2.0) + distanceY.toDouble().pow(2.0))
        angle = acos(distanceX / c)
        if (distanceY < 0) {
            angle = -angle
        }
        mProgressSweep = (90 - angle * 180 / Math.PI).toFloat()
        if (mProgressSweep < 0) mProgressSweep += 360f
        val progress = (mProgressSweep * valuePerDegree()).roundToInt()
        updateProgress(progress)
    }

   /* override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

                // start moving the thumb (this is the first touch)
                val x = event.x.toInt()
                val y = event.y.toInt()
                if (x < mThumbX + mThumbSize && x > mThumbX - mThumbSize && y < mThumbY + mThumbSize && y > mThumbY - mThumbSize) {
                    parent.requestDisallowInterceptTouchEvent(true)
                    mIsThumbSelected = true
                    updateProgressState(x, y)
                    if (mOnSeekBarChangeListener != null) {
                        mOnSeekBarChangeListener?.onStartTrackingTouch(this)
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {

                // still moving the thumb (this is not the first touch)
                if (mIsThumbSelected) {
                    val x = event.x.toInt()
                    val y = event.y.toInt()
                    updateProgressState(x, y)
                }
            }
            MotionEvent.ACTION_UP -> {

                // finished moving (this is the last touch)
                parent.requestDisallowInterceptTouchEvent(false)
                mIsThumbSelected = false
                if (mOnSeekBarChangeListener != null) mOnSeekBarChangeListener?.onStopTrackingTouch(
                    this
                )
            }
        }

        // redraw the whole component
        return true
    }*/

    private fun updateProgress(progress: Int) {

        // detect points change closed to max or min
        var tempProgress = progress
        val maxDetectValue = (mMax.toDouble() * 0.99).toInt()
        val minDetectValue = (mMax.toDouble() * 0.005).toInt() + mMin
        mUpdateTimes++
        if (tempProgress.toFloat() == INVALID_VALUE) {
            return
        }

        // avoid accidentally touch to become max from original point
        if (tempProgress > maxDetectValue && mPreviousProgress == INVALID_VALUE) {
            return
        }


        // record previous and current progress change
        if (mUpdateTimes == 1) {
            currentProgress = tempProgress.toFloat()
        } else {
            mPreviousProgress = currentProgress
            currentProgress = tempProgress.toFloat()
        }
        mProgressDisplay = tempProgress - tempProgress % step
        /**
         * Determine whether reach max or min to lock point update event.
         *
         * When reaching max, the progress will drop from max (or maxDetectPoints ~ max
         * to min (or min ~ minDetectPoints) and vice versa.
         *
         * If reach max or min, stop increasing / decreasing to avoid exceeding the max / min.
         */
        if (mUpdateTimes > 1 && !isMin && !isMax) {
            if (mPreviousProgress >= maxDetectValue && currentProgress <= minDetectValue && mPreviousProgress > currentProgress) {
                isMax = true
                tempProgress = mMax
                mProgressDisplay = mMax
                mProgressSweep = 360f
                if (mOnSeekBarChangeListener != null) {
                    mOnSeekBarChangeListener?.onPointsChanged(this, tempProgress, true)
                }
                invalidate()
            } else if (currentProgress >= maxDetectValue && mPreviousProgress <= minDetectValue && currentProgress > mPreviousProgress || currentProgress <= mMin) {
                isMin = true
                tempProgress = mMin
                mProgressDisplay = mMin
                mProgressSweep = mMin / valuePerDegree()
                if (mOnSeekBarChangeListener != null) {
                    mOnSeekBarChangeListener?.onPointsChanged(this, tempProgress, true)
                }
                invalidate()
            }
        } else {

            // Detect whether decreasing from max or increasing from min, to unlock the update event.
            // Make sure to check in detect range only.
            if (isMax and (currentProgress < mPreviousProgress) && currentProgress >= maxDetectValue) {
                isMax = false
            }
            if (isMin
                && mPreviousProgress < currentProgress
                && mPreviousProgress <= minDetectValue && currentProgress <= minDetectValue && mProgressDisplay >= mMin
            ) {
                isMin = false
            }
        }
        if (!isMax && !isMin) {
            tempProgress = if (tempProgress > mMax) mMax else tempProgress
            tempProgress = if (tempProgress < mMin) mMin else tempProgress
            if (mOnSeekBarChangeListener != null) {
                tempProgress -= tempProgress % step
                mOnSeekBarChangeListener?.onPointsChanged(this, tempProgress, true)
            }
            invalidate()
        }
    }

    fun setSeekBarChangeListener(seekBarChangeListener: OnSeekBarChangedListener?) {
        mOnSeekBarChangeListener = seekBarChangeListener
    }

    interface OnSeekBarChangedListener {
        /**
         * Notification that the point value has changed.
         *
         * @param circleSeekBar The CircleSeekBar view whose value has changed
         * @param points        The current point value.
         * @param fromUser      True if the point change was triggered by the user.
         */
        fun onPointsChanged(circleSeekBar: CircleSeekBar?, points: Int, fromUser: Boolean)
        fun onStartTrackingTouch(circleSeekBar: CircleSeekBar?)
        fun onStopTrackingTouch(circleSeekBar: CircleSeekBar?)
    }

    companion object {
        const val MIN = 0
        const val MAX = 100
        private const val ANGLE_OFFSET = -90
        private const val INVALID_VALUE = -1f
        private const val TEXT_SIZE_DEFAULT = 72
    }
}