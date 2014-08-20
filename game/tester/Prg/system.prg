//----------------------------------------------------------------------
//
// RPG Toolkit Version 3.1.0 System Library.
//
//----------------------------------------------------------------------
//
// The author of this file hereby forfeits any claim of copyright
// (economic, moral, or other) to this file and places it into the
// public domain. Among other things, it may be used, modified, and
// redistributed without any attribution or restriction. You may
// remove this notice, but it is recommended that you maintain it.
//
//----------------------------------------------------------------------
//
// To use the methods in this file from your programs, you can either
// use the include preprocessor at the start of the file like this:
//
//     #include "system.prg"
//     pause(); // Waits for a key.
//
// Or you can use "implict includes" by naming the library and the
// method separated by a dot, like this:
//
//     // Waits for a key, even without including system.prg.
//     system.pause();
//
//----------------------------------------------------------------------

//----------------------------------------------------------------------
//
// Ignores key strokes until a key other than a direction
// is pressed. The key that ends the waiting is returned.
//
// This is probably the most commonly used method in the
// system library.
//
//----------------------------------------------------------------------
method pause() {
    local(key) = "LEFT";
    until (key ~= "LEFT"  && _
            key ~= "RIGHT" && _
            key ~= "UP"    && _
            key ~= "DOWN") {
        key = wait();
    }
    return key;
}

//----------------------------------------------------------------------
//
// This class allows you to construct a single object that holds
// an array. The point of this class is so that you can make a
// a method that accepts an array as a parameter or that returns
// an array.
//
// For example, you might declare a method that adds up a set of
// numbers and returns the result like this:
//
//     method sum(CArray numbers) {
//         local(bound) = numbers->getBound();
//         local(sum) = 0;
//         for (local(i) = 0; i <= bound; ++i) {
//             sum += numbers[i];
//         }
//         return sum;
//     }
//
// The method can be called like this:
//
//     arr = CArray();
//     arr[0] = 4;
//     arr[1] = 2;
//     arr[2] = 3;
//     mwin(sum(arr)); // Shows 9.
//     arr->release();
//
//----------------------------------------------------------------------
class CArray
{

public:

    //
    // Default constructor.
    //
    method CArray() {
        // Create dynamic array
        m_bound = -1;
        m_isDynamic = true;
    }

    //
    // Construct an array that has a limited size.
    //
    method CArray(upperBound) {
        if (upperBound >= 0) {
            // Bound is okay
            m_bound = upperBound
        } else {
            // Bound is no good
            debugger("Invalid upper bound: " + CastLit(upperBound))
        }
    }

    //
    // This method allows you to make a copy of the array.
    //
    method clone(CArray rhs) {
        local(bound) = rhs->getBound();
        if (m_isDynamic = rhs->isDynamic()) {
            local(ret) = CArray();
        } else {
            local(ret) = CArray(bound);
        }
        for (local(i) = 0; i <= bound; ++i) {
            ret[i] = rhs[i];
        }
        return ret;
    }

    //
    // This method allows to set the array to another array.
    //
    method operator=(CArray rhs) {
        clean();
        local(bound) = rhs->getBound();
        m_bound = bound;
        m_isDynamic = rhs->isDynamic();
        for (local(i) = 0; i <= bound; i++) {
            this[i] = rhs[i];
        }
    }

    //
    // This method allows you to use the array as though it
    // were a normal array.
    //
    method operator[](idx) {
        if (m_isDynamic) {
            // Change bound if required
            if (idx > m_bound) {
                // It needs to be changed
                m_bound = idx;
            }

            // Return the element
            return &m_data[idx];
        }

        // Check bounds
        if (idx <= m_bound && idx >= 0) {
            // Within bounds
            return &m_data[idx];
        }

        // Out of bounds
        debugger("Out of array bounds: " + idx);
        global(g_null) = 0;
        return &g_null;
    }

    //
    // Return the highest index that has been set in the array.
    //
    method getBound() {
        return m_bound;
    }

    //
    // Resize the array.
    //
    method resize(newSize) {
        if (newSize >= 0) {
            // Bound is okay
            m_bound = newSize;
        }
    }

    //
    // Determine whether the array is dyamic
    //
    method isDynamic() {
        return m_isDynamic;
    }

    //
    // Change dynamic state (true or false)
    //
    method setDynamicState(bState) {
        m_isDynamic = bState;
    }

    //
    // Deconstruct the array.
    //
    method ~CArray() {
        // Clean out the array
        clean();
    }

private:

    //
    // Clean out this array.
    //
    method clean() {
        for (local(idx) = 0; idx <= m_bound; idx++) {
            // Kill this member.
            kill(m_data[idx]);
        }
        kill(m_bound, m_isDynamic);
    }

    var m_data[];        // Main data
    var m_bound;        // Upper bound of the array
    var m_isDynamic;    // Is a dynamic array?

}

